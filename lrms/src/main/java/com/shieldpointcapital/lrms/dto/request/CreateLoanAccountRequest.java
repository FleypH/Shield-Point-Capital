package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLoanAccountRequest(

        /* loanAccountId, createdAt, createdBy — generated/stamped server-side.

           accountStatus — defaults to ACTIVE in the service, not client-set.

           allocatedCapital — NOT set here. A new account starts unfunded;
           capital comes later via AllocateCapitalRequest, keeping "create
           an account" and "fund an account" as separate, single-purpose
           actions.

           assignedOfficer — NOT set here. A new account starts unassigned;
           officer assignment happens later via AssignOfficerRequest, same
           reasoning as above.

           totalLoansDisbursed, activeLoansCount, overdueLoansCount,
           defaultedLoansCount, fullyPaidLoansCount, projectedRevenue,
           realisedRevenue, revenueAtRisk, totalLosses,
           totalInterestForegone, defaultRate, collectionRate,
           currentCapital, capitalDeployed — all running counters/metrics
           that start at zero and are updated by other operations over
           the account's lifetime, never set at creation. */

        @NotBlank(message = "Account name is required")
        @Size(max = 100)
        String accountName

) {}