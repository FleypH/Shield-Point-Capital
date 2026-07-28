package com.shieldpointcapital.lrms.dto.request;

import com.shieldpointcapital.lrms.domain.enums.ConflictResolution;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ConflictResolutionRequest(

        @NotNull(message = "Conflict ID is required")
        Long conflictId,

        @NotNull(message = "Resolution is required")
        ConflictResolution resolution,

        /* resolvedData is intentionally nullable at the DTO level —
           only meaningful when resolution == MANUAL. Bean Validation
           can't express "required only if another field has X value,"
           so that check happens in the service layer instead. */
        Map<String, Object> resolvedData

) {}
