-- =============================================================
-- V13__add_performance_indexes.sql
-- Shield Point Capital — Loan Record Management System
-- Adds indexes for query performance
-- All tables must exist before indexes can be created
-- =============================================================

-- staff_account indexes
CREATE INDEX idx_staff_role
    ON staff_account(role);

CREATE INDEX idx_staff_is_active
    ON staff_account(is_active);

CREATE INDEX idx_staff_loan_account
    ON staff_account(loan_account_id);

-- loan_account indexes
CREATE INDEX idx_loan_account_status
    ON loan_account(account_status);

CREATE INDEX idx_loan_account_officer
    ON loan_account(assigned_officer_id);

-- user_info indexes
CREATE INDEX idx_user_info_full_name
    ON user_info(full_name);

-- loan_tracking indexes
CREATE INDEX idx_loan_tracking_id_number
    ON loan_tracking(id_number);

CREATE INDEX idx_loan_tracking_account
    ON loan_tracking(loan_account_id);

CREATE INDEX idx_loan_tracking_status
    ON loan_tracking(loan_status);

CREATE INDEX idx_loan_tracking_maturity
    ON loan_tracking(maturity_date);

CREATE INDEX idx_loan_tracking_days_past_due
    ON loan_tracking(days_past_due);

-- payment_log indexes
CREATE INDEX idx_payment_log_tracking
    ON payment_log(tracking_id);

CREATE INDEX idx_payment_log_date
    ON payment_log(payment_date);

-- loan_status_history indexes
CREATE INDEX idx_loan_status_history_tracking
    ON loan_status_history(tracking_id);

-- staff_audit_log indexes
CREATE INDEX idx_staff_audit_log_staff
    ON staff_audit_log(staff_id);

CREATE INDEX idx_staff_audit_log_action_type
    ON staff_audit_log(action_type);

CREATE INDEX idx_staff_audit_log_timestamp
    ON staff_audit_log(action_timestamp);

-- company_ledger indexes
CREATE INDEX idx_company_ledger_account
    ON company_ledger(loan_account_id);

CREATE INDEX idx_company_ledger_tracking
    ON company_ledger(tracking_id);

CREATE INDEX idx_company_ledger_event_type
    ON company_ledger(event_type);

CREATE INDEX idx_company_ledger_event_date
    ON company_ledger(event_date);

-- sync_record indexes
CREATE INDEX idx_sync_record_device
    ON sync_record(device_id);

CREATE INDEX idx_sync_record_staff
    ON sync_record(staff_id);

-- device_session indexes
CREATE INDEX idx_device_session_staff
    ON device_session(staff_id);

-- conflict_log indexes
CREATE INDEX idx_conflict_log_device
    ON conflict_log(device_id);

CREATE INDEX idx_conflict_log_staff
    ON conflict_log(staff_id);