package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AllocateCapitalRequest(

        /* No loanAccountId field — it comes from the URL path variable
           (POST /loan-accounts/{loanAccountId}/allocate-capital), so
           including it in the body would be redundant and risks the
           path and body disagreeing on which account is being funded. */

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        /* occurredAt is OPTIONAL, same offline/online pattern as
           InjectCapitalRequest: client sends its device timestamp when
           offline, omits it when online and the server stamps now(). */
        LocalDateTime occurredAt,

        // Nullable — optional note on why this allocation was made
        String remarks

) {}