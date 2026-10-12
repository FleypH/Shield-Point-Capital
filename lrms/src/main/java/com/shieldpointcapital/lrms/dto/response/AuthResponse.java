package com.shieldpointcapital.lrms.dto.response;

public record AuthResponse(
    String staffId,
    String role,
    String token,
    String firstName,
    String lastName,
    String loanAccountId,   // null for ADMIN/MANAGER/ACCOUNTANT
    Long expiresAt
){}