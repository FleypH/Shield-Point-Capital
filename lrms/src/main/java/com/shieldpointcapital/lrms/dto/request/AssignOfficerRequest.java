package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * ADMIN-only. Reassigns a loan_account (portfolio) to a different staff
 * member — per  business rules, loan accounts are portfolios, not
 * officers; borrowers follow the account automatically when it moves.
 * loanAccountId comes from the path variable in the controller, not this
 * body, so it isn't duplicated here.
 */

public record AssignOfficerRequest(    
    @NotBlank(message = "New staff ID is required")
    String newStaffId,

    // Optional — recommended for staff_audit_log.description
    String reason
){}
