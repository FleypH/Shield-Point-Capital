package com.shieldpointcapital.lrms.security;

import com.shieldpointcapital.lrms.domain.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionEvaluator {
    
    // Staff managemnt

    public boolean canCreateStaff(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canDeactivateStaff(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canChangeRole(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canViewAllStaff(Role role) {
        return role == Role.ADMIN || role == Role.MANAGER;
    }

    // Loan account management

    public boolean canCreateLoanAccount(Role role){
        return role == Role.ADMIN;
    }

    public boolean canAllocateCapital(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canAssignOfficer(Role role) {
        return role == Role.ADMIN;
    }

    //Borrower management

    public boolean canCreateBorrower(Role role){
        return 
        role == Role.ADMIN || 
        role == Role.MANAGER ||
        role == Role.LOAN_OFFICER;
    }

    public boolean canViewBorrower(Role role){
        // all rles can view borrower details
        return true;
    }

    // loan management

    public boolean canDisburseLoan(Role role) {
        return 
        role == Role.ADMIN ||
        role == Role.MANAGER ||
        role == Role.LOAN_OFFICER;
    }


    public boolean canRecordPayment(Role role) {
        return role == Role.ADMIN ||
               role == Role.MANAGER ||
               role == Role.LOAN_OFFICER;
    }

    public boolean canWriteOffLoan(Role role) {
        // Only admin and manager can write off loans
        return role == Role.ADMIN || role == Role.MANAGER;
    }

    public boolean canRestructureLoan(Role role) {
        return role == Role.ADMIN || role == Role.MANAGER;
    }

    // ─── Financial reporting ─────────────────────────────────────

    public boolean canViewFinancials(Role role) {
        return role == Role.ADMIN ||
               role == Role.MANAGER ||
               role == Role.ACCOUNTANT;
    }

    public boolean canViewLedger(Role role) {
        return role == Role.ADMIN ||
               role == Role.MANAGER ||
               role == Role.ACCOUNTANT;
    }

    public boolean canViewReports(Role role) {
        return role == Role.ADMIN ||
               role == Role.MANAGER ||
               role == Role.ACCOUNTANT;
    }
}
