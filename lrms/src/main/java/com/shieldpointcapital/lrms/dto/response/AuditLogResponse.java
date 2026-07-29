package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.ActionType;

import java.time.LocalDateTime;

public record AuditLogResponse(

        Long logId,

        // staffAccount is a full @ManyToOne relationship on the entity —
        // same as LoanAccountResponse's assignedOfficer — so both fields
        // come straight off it via MapStruct's nested traversal, no
        // second repository lookup needed.
        String staffId,
        String staffName,

        ActionType actionType,
        String targetTable,
        String targetId,
        LocalDateTime actionTimestamp,
        String ipAddress,

        // The actual point of this table per your doc — before/after
        // JSON snapshots that make unauthorised changes detectable.
        String previousValue,
        String newValue,

        String notes

) {}