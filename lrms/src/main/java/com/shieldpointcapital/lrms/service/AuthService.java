package com.shieldpointcapital.lrms.service;

import com.shieldpointcapital.lrms.dto.request.LoginRequest;
import com.shieldpointcapital.lrms.dto.response.AuthResponse;
import com.shieldpointcapital.lrms.dto.request.ChangePasswordRequest;

public interface AuthService { 
    void changePassword(String staffId, ChangePasswordRequest request);

    void logout();

    AuthResponse login(LoginRequest request);
}