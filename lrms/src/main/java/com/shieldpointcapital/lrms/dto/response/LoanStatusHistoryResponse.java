package com.shieldpointcapital.lrms.dto.response;

import java.time.LocalDateTime;

public record LoanStatusHistoryResponse(

        Long historyId,
        Long trackingId,

        String previousStatus,   // String, matching the entity's raw column type — null for the first ACTIVE entry
        String newStatus,

        LocalDateTime changeDate,
        String changedByName,    // resolved by the service via a second StaffAccount lookup

        String reason

) {}