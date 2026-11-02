package com.shieldpointcapital.lrms.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.shieldpointcapital.lrms.service.AuthServiceImpl;
import com.shieldpointcapital.lrms.dto.request.LoginRequest;
import com.shieldpointcapital.lrms.dto.response.AuthResponse;
import com.shieldpointcapital.lrms.dto.request.ChangePasswordRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        return authServiceImpl.login(request);
    }

    @PostMapping("/logout")
    public void logout() {
        authServiceImpl.logout();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        String staffId = SecurityContextHolder.getContext().getAuthentication().getName();
        authServiceImpl.changePassword(staffId, request);
        return ResponseEntity.noContent().build();
    }

}
