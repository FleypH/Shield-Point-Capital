package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.EventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LedgerEntryResponse(

        Long ledgerId,

        // loanAccount / loanTracking are full @ManyToOne relationships
        // on the entity — MapStruct traverses these directly, no
        // second repository lookup needed.
        String loanAccountId,
        Long trackingId,

        EventType eventType,
        LocalDate eventDate,

        String recordedByName,   // resolved by the service via a second StaffAccount lookup
        LocalDateTime recordedAt,

        BigDecimal capitalEffect,
        BigDecimal principalComponent,
        BigDecimal interestComponent,
        BigDecimal penaltyComponent,
        BigDecimal lossAmount,
        BigDecimal revenueEffect,

        BigDecimal subAccountBalanceAfter,
        BigDecimal masterBalanceAfter,

        String notes

) {}