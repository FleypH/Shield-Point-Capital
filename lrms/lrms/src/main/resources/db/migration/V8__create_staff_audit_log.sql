-- =============================================================
-- V8__create_staff_audit_log.sql
-- Shield Point Capital — Loan Record Management System
-- INSERT only — immutable staff action log
-- References: staff_account(staff_id)
-- =============================================================

CREATE TABLE staff_audit_log (

    -- Primary key
    log_id              BIGINT          NOT NULL AUTO_INCREMENT,

    -- Staff reference
    staff_id            VARCHAR(20)     NOT NULL,

    -- Action details
    action_type         ENUM(
                            'LOGIN',
                            'LOGOUT',
                            'LOGIN_FAILED',
                            'ACCOUNT_LOCKED',
                            'CREATE_BORROWER',
                            'RECORD_PAYMENT',
                            'UPDATE_RECORD',
                            'VIEW_FINANCIALS',
                            'WRITE_OFF_LOAN',
                            'CREATE_STAFF',
                            'CHANGE_ROLE',
                            'DEACTIVATE_STAFF'
                        )               NOT NULL,
    target_table        VARCHAR(50)     NULL,
    target_id           VARCHAR(50)     NULL,
    action_timestamp    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip_address          VARCHAR(45)     NULL,

    -- Before and after snapshots
    previous_value      TEXT            NULL,
    new_value           TEXT            NULL,
    notes               TEXT            NULL,

    -- Constraints
    CONSTRAINT pk_staff_audit_log PRIMARY KEY (log_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_staff_audit_log_staff → staff_account(staff_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;