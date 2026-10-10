-- =============================================================
-- V7__create_loan_status_history.sql
-- Shield Point Capital — Loan Record Management System
-- INSERT only — audit trail of every loan status change
-- First row: previous_status = NULL, new_status = ACTIVE
-- References: loan_tracking(tracking_id)
-- =============================================================

CREATE TABLE loan_status_history (

    -- Primary key
    history_id          BIGINT          NOT NULL AUTO_INCREMENT,

    -- Links to specific loan
    tracking_id         BIGINT          NOT NULL,

    -- Status change
    previous_status     VARCHAR(20)     NULL,
    new_status          VARCHAR(20)     NOT NULL,
    change_date         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by          VARCHAR(20)     NOT NULL,
    reason              TEXT            NULL,

    -- Constraints
    CONSTRAINT pk_loan_status_history PRIMARY KEY (history_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_loan_status_history_tracking → loan_tracking(tracking_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;