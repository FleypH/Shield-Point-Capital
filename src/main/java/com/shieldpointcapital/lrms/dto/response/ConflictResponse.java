package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.ConflictResolution;

import java.time.LocalDateTime;

public record ConflictResponse(

        Long conflictId,

        String deviceId,
        String staffId,          // raw ID — admin/technical review screen, no name lookup

        String tableName,
        String recordId,

        // Raw JSON strings, same shape as stored — client re-parses
        // if it needs structured access. Avoids a service-side
        // deserialization step that could throw on malformed JSON.
        String clientValue,
        String serverValue,

        LocalDateTime conflictDetectedAt,
        LocalDateTime resolvedAt,   // null until resolved
        String resolvedBy,          // raw ID, null until resolved
        ConflictResolution resolution // null until resolved

) {}