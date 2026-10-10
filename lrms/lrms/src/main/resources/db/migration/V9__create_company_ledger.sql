-- =============================================================
-- V9__create_company_ledger.sql
-- Shield Point Capital — Loan Record Management System
-- INSERT only — permanent financial event log
-- References: loan_account(loan_account_id),
--             loan_tracking(tracking_id)
-- =============================================================

CREATE TABLE company_ledger (

    -- Primary key
    ledger_id                   BIGINT          NOT NULL AUTO_INCREMENT,

    -- References
    loan_account_id             VARCHAR(20)     NOT NULL,
    tracking_id                 BIGINT          NOT NULL,

    -- Event details
    event_type                  ENUM(
                                    'LOAN_DISBURSED',
                                    'PAYMENT_RECEIVED',
                                    'LOAN_PAID_FULL',
                                    'LOAN_OVERDUE_FLAGGED',
                                    'LOAN_WRITTEN_OFF',
                                    'PENALTY_APPLIED',
                                    'CAPITAL_ALLOCATED',
                                    'CAPITAL_INJECTED'
                                )               NOT NULL,
    event_date                  DATE            NOT NULL,
    recorded_by                 VARCHAR(20)     NOT NULL,
    recorded_at                 DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Financial components
    capital_effect              DECIMAL(15,2)   NOT NULL,
    principal_component         DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    interest_component          DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    penalty_component           DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    loss_amount                 DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    revenue_effect              DECIMAL(15,2)   NOT NULL,

    -- Balance snapshots at time of event
    sub_account_balance_after   DECIMAL(15,2)   NOT NULL,
    master_balance_after        DECIMAL(15,2)   NOT NULL,

    notes                       TEXT            NULL,

    -- Constraints
    CONSTRAINT pk_company_ledger PRIMARY KEY (ledger_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_company_ledger_account  → loan_account(loan_account_id)
    -- fk_company_ledger_tracking → loan_tracking(tracking_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;