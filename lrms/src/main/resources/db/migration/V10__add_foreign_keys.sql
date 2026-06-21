-- =============================================================
-- V10__add_foreign_keys.sql
-- Shield Point Capital — Loan Record Management System
-- Adds all foreign key constraints after all tables exist
-- Resolves circular FK between staff_account and loan_account
-- =============================================================

-- staff_account → loan_account
ALTER TABLE staff_account
    ADD CONSTRAINT fk_staff_loan_account
    FOREIGN KEY (loan_account_id)
    REFERENCES loan_account(loan_account_id);

-- loan_account → company_account
ALTER TABLE loan_account
    ADD CONSTRAINT fk_loan_account_master
    FOREIGN KEY (master_account_id)
    REFERENCES company_account(account_id);

-- loan_account → staff_account (circular — safe now both tables exist)
ALTER TABLE loan_account
    ADD CONSTRAINT fk_loan_account_officer
    FOREIGN KEY (assigned_officer_id)
    REFERENCES staff_account(staff_id);

-- user_info has no FKs — standalone identity table

-- loan_tracking → user_info
ALTER TABLE loan_tracking
    ADD CONSTRAINT fk_loan_tracking_borrower
    FOREIGN KEY (id_number)
    REFERENCES user_info(id_number);

-- loan_tracking → loan_account
ALTER TABLE loan_tracking
    ADD CONSTRAINT fk_loan_tracking_account
    FOREIGN KEY (loan_account_id)
    REFERENCES loan_account(loan_account_id);

-- payment_log → loan_tracking
ALTER TABLE payment_log
    ADD CONSTRAINT fk_payment_log_tracking
    FOREIGN KEY (tracking_id)
    REFERENCES loan_tracking(tracking_id);

-- loan_status_history → loan_tracking
ALTER TABLE loan_status_history
    ADD CONSTRAINT fk_loan_status_history_tracking
    FOREIGN KEY (tracking_id)
    REFERENCES loan_tracking(tracking_id);

-- staff_audit_log → staff_account
ALTER TABLE staff_audit_log
    ADD CONSTRAINT fk_staff_audit_log_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff_account(staff_id);

-- company_ledger → loan_account
ALTER TABLE company_ledger
    ADD CONSTRAINT fk_company_ledger_account
    FOREIGN KEY (loan_account_id)
    REFERENCES loan_account(loan_account_id);

-- company_ledger → loan_tracking
ALTER TABLE company_ledger
    ADD CONSTRAINT fk_company_ledger_tracking
    FOREIGN KEY (tracking_id)
    REFERENCES loan_tracking(tracking_id);