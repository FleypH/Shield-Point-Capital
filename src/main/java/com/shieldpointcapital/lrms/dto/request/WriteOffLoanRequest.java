package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WriteOffLoanRequest(

        /* writtenOffBy is deliberately excluded — the server determines
           who approved this from the authenticated caller's JWT claims,
           never from a client-supplied field. */

        @NotNull(message = "Loan tracking ID is required")
        Long trackingId,

        @NotBlank(message = "A write-off reason is required")
        @Size(min = 10, max = 500, message = "Reason must be between 10 and 500 characters")
        String reason

) {}