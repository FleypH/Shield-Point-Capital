package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(

        Long trackingId,
        String borrowerName,     // concatenated firstName + lastName, computed in the mapper
        String loanAccountId,    // nested — comes from entity.getLoanAccount().getLoanAccountId()

        String loanType,
        BigDecimal disbursementAmount,
        BigDecimal totalRepaymentAmount,
        BigDecimal outstandingBalance,
        BigDecimal amountPaid,
        BigDecimal totalAmountDue,
        BigDecimal penaltyAccrued,

        LoanStatus loanStatus,
        LocalDate disbursementDate,
        LocalDate maturityDate,
        Integer daysPastDue

) {}