-- =============================================================
-- V6__create_payment_log.sql
-- Shield Point Capital — Loan Record Management System
-- INSERT only — rows are never modified after creation
-- References: loan_tracking(tracking_id)
-- =============================================================

CREATE TABLE payment_log (

    -- Primary key
    payment_id          BIGINT          NOT NULL AUTO_INCREMENT,

    -- Links to specific loan not just borrower
    tracking_id         BIGINT          NOT NULL,

    -- Payment details
    amount_received     DECIMAL(15,2)   NOT NULL,
    payment_date        DATE            NOT NULL,
    payment_method      VARCHAR(30)     NOT NULL,
    payment_reference   VARCHAR(100)    NULL,

    -- Audit
    recorded_by         VARCHAR(20)     NOT NULL,
    recorded_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes               TEXT            NULL,

    -- Constraints
    CONSTRAINT pk_payment_log PRIMARY KEY (payment_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_payment_log_tracking → loan_tracking(tracking_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;