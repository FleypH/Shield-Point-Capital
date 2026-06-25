package com.shieldpointcapital.lrms.security;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
        HttpServerRequest request,
        HttpServerResponse response,
        FilterChain filterChain) throws ServletException, IOException {



    
    )
}
