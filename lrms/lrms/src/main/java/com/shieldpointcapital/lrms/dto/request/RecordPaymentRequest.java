package com.shieldpointcapital.lrms.dto.request;

import com.shieldpointcapital.lrms.domain.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecordPaymentRequest(

        /* recordedBy is deliberately excluded — determined server-side
           from the authenticated caller's JWT claims. */

        @NotNull(message = "Loan tracking ID is required")
        Long trackingId,

        @NotNull(message = "Amount paid is required")
        @DecimalMin(value = "0.01", message = "Amount paid must be greater than zero")
        BigDecimal amountPaid,

        @NotNull(message = "Payment date is required")
        LocalDate paymentDate,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        // Nullable — not every payment method produces an external reference
        // (e.g. a cash payment recorded in person might not have one)
        String paymentReference

) {}