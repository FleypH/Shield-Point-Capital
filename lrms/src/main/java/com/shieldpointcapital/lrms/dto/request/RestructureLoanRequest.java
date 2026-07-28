package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RestructureLoanRequest(

        /* loanStatus is deliberately excluded — calling this endpoint IS
           the decision to restructure; the service sets status to
           RESTRUCTURED automatically, the client doesn't declare it. */

        @NotNull(message = "Loan tracking ID is required")
        Long trackingId,

        /* totalRepaymentAmount, outstandingBalance, totalAmountDue,
           daysPastDue, and penaltyAccrued are deliberately excluded —
           all recalculated server-side from the new maturity date and
           the loan's current state, never trusted from the client. */

        @NotNull(message = "New maturity date is required")
        LocalDate newMaturityDate,

        @NotBlank(message = "A restructure reason is required")
        @Size(min = 10, max = 500, message = "Reason must be between 10 and 500 characters")
        String reason

) {}