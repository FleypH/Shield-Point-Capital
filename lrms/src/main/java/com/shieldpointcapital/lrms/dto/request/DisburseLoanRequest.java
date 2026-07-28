package com.shieldpointcapital.lrms.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DisburseLoanRequest(
    @NotBlank(message = "Borrower ID number required")
    @Pattern(regexp = "^\\d{2}-\\d{6,7}[A-Z]\\d{2}$", message = "National ID must match Zimbabwe format e.g. 63-123456N71")
    String  borrowerId,

    @NotBlank(message = "Loan Account ID required")
    String LoanAccount,

    @NotBlank(message = "Loan type required")
    @Size(max = 50)
    String loanType,

    @NotBlank(message = "Date Required")
    @Past(message = "Disbursement date must be current date")
    LocalDate disbursementDate,

    @NotBlank(message = "Amount to be disbursed required")
    BigDecimal disbursementAmount,

    @NotBlank(message = "maturity Date Required")
    @PastOrPresent(message = "Disbursement date must be current date")
    LocalDate maturityDate,

    @NotBlank(message = "Repayment amount required")
    BigDecimal repaymentAmount



) {
    
}
