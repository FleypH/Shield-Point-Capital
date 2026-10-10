package com.shieldpointcapital.lrms.dto.response;

public record AuthResponse(
    String staffId,
    String role,
    String token,
    String firstName,
    String lastName,
    Long expiresAt
){}
