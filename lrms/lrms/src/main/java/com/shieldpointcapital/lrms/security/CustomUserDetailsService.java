package com.shieldpointcapital.lrms.security;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffAccountRepository staffAccountRepository;

    // Spring Security calls this automatically during login
    // username here is actually the email address
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Find the staff member by email
        StaffAccount staff = staffAccountRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(
                "No staff account found with email: " + email
            ));

        // Convert our StaffAccount into Spring Security's UserDetails format
        // Spring Security needs this format to verify the password
        // and check if the account is active
        return User.builder()
            // Spring Security uses username for identification
            // we use email as the login username
            .username(staff.getEmail())
            // the bcrypt hashed password from database
            .password(staff.getPasswordHash())
            // the role — prefixed with ROLE_ as Spring Security requires
            .authorities(List.of(new SimpleGrantedAuthority(
                "ROLE_" + staff.getRole().name()
            )))
            // account is enabled only if is_active = true
            .accountExpired(false)
            .accountLocked(staff.getLockedUntil() != null &&
                staff.getLockedUntil().isAfter(
                    java.time.LocalDateTime.now()))
            .credentialsExpired(false)
            .disabled(!staff.getIsActive())
            .build();
    }
}