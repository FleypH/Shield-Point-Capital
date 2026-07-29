package com.shieldpointcapital.lrms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompanyDashboardResponse(

        // accountId excluded — singleton (Byte, always 1), carries no
        // information, same reasoning as skipping accountId on
        // InjectCapitalRequest earlier.

        String companyName,

        BigDecimal totalOpeningCapital,
        BigDecimal totalAllocatedCapital,
        BigDecimal unallocatedCapital,
        BigDecimal currentCapital,
        BigDecimal capitalDeployed,

        Integer totalLoansDisbursed,
        BigDecimal totalDisbursedAmount,
        Integer activeLoansCount,
        Integer overdueLoansCount,
        Integer defaultedLoansCount,
        Integer fullyPaidLoansCount,

        BigDecimal projectedRevenue,
        BigDecimal projectedRevenueOverdue,
        BigDecimal realisedRevenue,
        BigDecimal revenueAtRisk,
        BigDecimal totalLosses,
        BigDecimal totalInterestForegone,

        BigDecimal collectionRate,
        BigDecimal defaultRate,
        BigDecimal capitalUtilisationRate,

        LocalDateTime lastUpdated

) {}