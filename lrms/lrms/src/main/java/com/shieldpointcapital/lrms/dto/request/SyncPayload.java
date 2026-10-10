package com.shieldpointcapital.lrms.dto.request;

import com.shieldpointcapital.lrms.domain.enums.EntityType; // you'll need to define this enum: PAYMENT, BORROWER_UPDATE, etc.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

public record SyncPayload(

        // Client-generated local ID (e.g. WatermelonDB's local record ID) —
        // lets the server tell the app which local record this change maps
        // to when it responds, even before the server assigns a real ID.
        @NotBlank(message = "Local record ID is required")
        String localId,

        @NotNull(message = "Entity type is required")
        EntityType entityType,

        // Flexible payload — shape depends entirely on entityType.
        // Service layer parses this against the correct request DTO
        // (RecordPaymentRequest, UpdateBorrowerRequest, etc.) once it
        // knows the type.
        @NotNull(message = "Payload data is required")
        Map<String, Object> data,

        // Same offline pattern as InjectCapitalRequest — when this
        // change actually happened on the device, not when it synced.
        @NotNull(message = "Occurred-at timestamp is required")
        LocalDateTime occurredAt

) {}