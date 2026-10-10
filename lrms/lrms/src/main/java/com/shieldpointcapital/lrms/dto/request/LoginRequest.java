package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Staff Id is required")
    String staffId,

    @NotBlank(message ="Password is required")
    String password
){}
