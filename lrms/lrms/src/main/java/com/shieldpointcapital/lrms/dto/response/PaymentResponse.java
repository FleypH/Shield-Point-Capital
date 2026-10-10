package com.shieldpointcapital.lrms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentResponse(

        Long paymentId,
        Long trackingId,          // which loan this payment belongs to

        BigDecimal amountReceived,
        LocalDate paymentDate,
        String paymentMethod,     // String, matching the entity's current raw column type
        String paymentReference,

        String recordedByName,    // resolved by the service via a second StaffAccount lookup
        LocalDateTime recordedAt,

        String notes

) {}