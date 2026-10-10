package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanAccountResponse(

        String loanAccountId,
        String accountName,
        AccountStatus accountStatus,

        // assignedOfficer is a full @ManyToOne on the entity, so both
        // fields come straight off it — no second repository lookup
        // needed, unlike borrowerName/recordedByName elsewhere.
        String assignedOfficerId,
        String assignedOfficerName,
        LocalDateTime assignedAt,

        BigDecimal allocatedCapital,
        BigDecimal currentCapital,
        BigDecimal capitalDeployed,

        Integer totalLoansDisbursed,
        Integer activeLoansCount,
        Integer overdueLoansCount,
        Integer defaultedLoansCount,
        Integer fullyPaidLoansCount,

        BigDecimal projectedRevenue,
        BigDecimal realisedRevenue,
        BigDecimal revenueAtRisk,
        BigDecimal totalLosses,
        BigDecimal totalInterestForegone,

        BigDecimal defaultRate,
        BigDecimal collectionRate,

        LocalDateTime createdAt,
        String createdBy

) {}