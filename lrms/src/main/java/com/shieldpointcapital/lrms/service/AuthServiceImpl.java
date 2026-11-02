package com.shieldpointcapital.lrms.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.dto.request.ChangePasswordRequest;
import com.shieldpointcapital.lrms.dto.request.LoginRequest;
import com.shieldpointcapital.lrms.dto.response.AuthResponse;
import com.shieldpointcapital.lrms.exception.AccountLockedException;
import com.shieldpointcapital.lrms.exception.BadRequestException;
import com.shieldpointcapital.lrms.exception.InvalidCredentialsException;
import com.shieldpointcapital.lrms.exception.ResourceNotFoundException;
import com.shieldpointcapital.lrms.exception.UnauthorizedActionException;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;
import com.shieldpointcapital.lrms.security.JwtUtil;


@Service
public class AuthServiceImpl implements AuthService{
    private final StaffAccountRepository staffAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public AuthServiceImpl(StaffAccountRepository staffAccountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.staffAccountRepository = staffAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public AuthResponse login(LoginRequest request) {
        
        // 1. find the account
        StaffAccount staff = staffAccountRepository.findByEmail(request.email())
        .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
        
        // 2. Deactivatted accounts can't log in
        if (!staff.getIsActive()) {
            throw new UnauthorizedActionException("Account is Deactivated");
        }

        // 3. Locked accounts can't log in (until lockedUntill passes)
        if (staff.getLockedUntil() !=  null && staff.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new AccountLockedException("Account is Locked until " + staff.getLockedUntil());
        }

        // 4. Check password
        if (!passwordEncoder.matches(request.password(), staff.getPasswordHash())) {
            int attempts = staff.getFailedLoginCount() + 1;
            staff.setFailedLoginCount((byte) attempts);

            if (attempts >= 5) {
                staff.setLockedUntil(LocalDateTime.now().plusMinutes(15));
            }
            staffAccountRepository.save(staff);

            throw new InvalidCredentialsException("Invalid credentials");
        }

        // 5. Success: reset the failure counter and record the login
        staff.setFailedLoginCount((byte) 0);
        staff.setLockedUntil(null);
        staff.setLastLoginAt(LocalDateTime.now());
        staffAccountRepository.save(staff);
        

        //6. Build the token and the response
        String loanAccountId = (staff.getLoanAccount() != null) ? staff.getLoanAccount().getLoanAccountId() : null;
        String token = jwtUtil.generateToken(
            staff.getStaffId(),
            staff.getRole().name(),
            staff.getTokenVersion(),
            request.deviceId(),
            loanAccountId
        );
        
        long expiresAt = System.currentTimeMillis() + jwtExpiration;

        return new AuthResponse(
            staff.getStaffId(),
            staff.getRole().name(),
            token,
            staff.getFirstName(),
            staff.getLastName(),
            loanAccountId,
            expiresAt
        );

    }

    @Override
    @SuppressWarnings("null")
    public void logout() {

        String staffId = SecurityContextHolder.getContext().getAuthentication().getName();

        StaffAccount staff = staffAccountRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        staff.setTokenVersion(staff.getTokenVersion() + 1);
        staffAccountRepository.save(staff);

       /*auditService.log(staffId, ActionType.LOGOUT, null, null, null, null, null);*/
    }
    
    @Override 
    @SuppressWarnings("null")
    public void changePassword(String staffId, ChangePasswordRequest request) {

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        StaffAccount staff = staffAccountRepository.findById(staffId)
        .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        if (!passwordEncoder.matches(request.currentPassword(), staff.getPasswordHash())) {
            throw new BadRequestException("Current password is incorrect");
        }
        
        if (passwordEncoder.matches(request.newPassword(), staff.getPasswordHash())) {
            throw new BadRequestException("New password must be different from the current password");
        }

        staff.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        staff.setTokenVersion(staff.getTokenVersion() + 1);
        staff.setPasswordResetToken(null);
        staff.setTokenExpiresAt(null);
        staff.setUpdatedAt(LocalDateTime.now());
        staff.setUpdatedBy(staffId);

        staffAccountRepository.save(staff);
    }
}