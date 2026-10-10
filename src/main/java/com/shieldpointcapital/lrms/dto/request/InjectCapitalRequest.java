package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InjectCapitalRequest(

        /* No accountId field — company_account is a singleton
           (account_id = 1 always). */

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Capital source is required")
        String source,

        /* occurredAt is OPTIONAL:
             - offline path: client sends its device timestamp, the true
               moment the injection happened, since the server had no
               way to witness it.
             - online path: client omits this, server stamps
               LocalDateTime.now() in the service layer — no reason to
               trust a device clock when the server clock is authoritative
               and available in real time. */
        LocalDateTime occurredAt

) {}