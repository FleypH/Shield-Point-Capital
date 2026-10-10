package com.shieldpointcapital.lrms.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final StaffAccountRepository staffAccountRepository;

    // This method runs once for every HTTP request
    // before it reaches any controller
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
            // Step 1 — look for the Authorization header
            // Every authenticated request must include:
            // Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
            String authHeader = request.getHeader("Authorization");

            // Step 2 — if no token present just continue
            // SecurityConfig will decide if this endpoint needs auth
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Step 3 — extract the token
            // Remove "Bearer " prefix (7 characters) to get the raw token
            String token = authHeader.substring(7);

            // Step 4 — validate the token signature and expiry
            if (!jwtUtil.isTokenValid(token)) {
                // Token is expired or tampered — stop here
                // Do not call filterChain.doFilter()
                // Request dies here with 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is invalid or expired");
                return;
            }
            //Step 5- extract data from the token
            String staffId = jwtUtil.extractStaffId(token);
      
            Integer tokenVersion = jwtUtil.extractTokenVersion(token);

            //Step 6 load staff member from the database   
            if (staffId == null || staffId.isBlank()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token identity payload");
                return;
            }
            StaffAccount staff = staffAccountRepository.findById(staffId).orElse(null);
            
            //staff member no longer exists in database
            if (staff == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Staff account not found");
                return;
            }

            //step 7 check token version
            //this is how we invalidate tokens after logout or password reset without maintaing a blacklist
            //this is a feature that checks if the tokenVersion is not null 
            if (tokenVersion == null ) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token version");
                return;
            }
            if (!tokenVersion.equals(staff.getTokenVersion())){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Session expired. please login again");
                return;
            }

            //Step 9 check if account is locked
            if (staff.getLockedUntil() != null && staff.getLockedUntil().isAfter(java.time.LocalDateTime.now())){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Account is temporarily locked");
                return;
            }
            //step 10 all checks passed
            // tell spring security who this person is
            // this is called "setting the authentication context"
            // every subsequent piece of code in this request can now call SecurityContextHolder.getContext().setAuthentication(authentication);
            //to know who is making the request
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                staff, // the principal - the full staffAccount object
                null, // credentials - null because token already verified
                List.of(new SimpleGrantedAuthority("ROLE_" + staff.getRole().name()))
            );
             
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Place the authentication into Spring's security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // step 11 - pass request to the next filter or controller
            filterChain.doFilter(request, response);

        }

    
}

