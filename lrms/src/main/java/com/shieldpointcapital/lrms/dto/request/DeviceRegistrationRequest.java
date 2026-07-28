package com.shieldpointcapital.lrms.dto.request;

import com.shieldpointcapital.lrms.domain.enums.DevicePlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceRegistrationRequest(

        /* staffId — not a field here. JWT-derived, same as every other
           actor-identity field so far. */

        @NotBlank(message = "Device ID is required")
        String deviceId,

        @NotBlank(message = "Device name is required")
        String deviceName,   // e.g. "Phillip's Galaxy A54" — helps support tickets

        @NotNull(message = "Platform is required")
        DevicePlatform platform

) {}