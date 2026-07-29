package com.shieldpointcapital.lrms.dto.response;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record RevenueProjectionResponse(

        List<MonthlyProjection> projections

) {
    public record MonthlyProjection(

            YearMonth month,

            BigDecimal expectedPrincipal,
            BigDecimal expectedInterest,
            BigDecimal expectedTotal,

            // per your calc rule: revenue projection excludes written-off
            // loans, but overdue loans are included and flagged as at-risk
            BigDecimal atRiskAmount

    ) {}
}