package com.shieldpointcapital.lrms.dto.response;

import java.time.LocalDateTime;

public record DeviceSessionResponse(

        Long sessionId,

        String deviceId,
        String deviceName,

        // staffId is a raw column here, NOT a relationship — unlike
        // LoanAccount's assignedOfficer. Needs a second repository
        // lookup in the service, same as recordedByName elsewhere.
        String staffId,
        String staffName,

        LocalDateTime registeredAt,
        LocalDateTime lastSeenAt,
        boolean isActive

) {}