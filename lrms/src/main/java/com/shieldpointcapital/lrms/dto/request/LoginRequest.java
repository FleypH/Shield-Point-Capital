package com.shieldpointcapital.lrms.dto.request;

import com.shieldpointcapital.lrms.domain.enums.DevicePlatform;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,

    @NotBlank(message ="Password is required")
    String password,

    @NotBlank(message = "Device ID is required")
        String deviceId,

    @NotNull(message = "Platform is required")
    DevicePlatform platform

){}
