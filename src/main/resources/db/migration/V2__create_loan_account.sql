-- Referenced by: staff_account, user_info, loan_tracking,
--                loan_status_history, staff_audit_log
-- NOTE: FK to company_account added in V10__add_foreign_keys.sql
-- NOTE: FK to staff_account added in V10__add_foreign_keys.sql

CREATE TABLE loan_account (

    -- Primary identification
    loan_account_id         VARCHAR(20)     NOT NULL,
    master_account_id       TINYINT         NOT NULL DEFAULT 1,
    account_name            VARCHAR(100)    NOT NULL,
    account_status          ENUM('ACTIVE','SUSPENDED','TERMINATED')NOT NULL DEFAULT 'ACTIVE',

    -- Officer assignment
    assigned_officer_id         VARCHAR(20)     NULL,
    assigned_at                 DATETIME        NULL,
    previously_assigned_to      VARCHAR(20)     NULL,

    -- Capital tracking
    allocated_capital           DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    current_capital             DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    capital_deployed            DECIMAL(15,2)   NOT NULL DEFAULT 0.00,

    -- Loan counts
    total_loans_disbursed       INT             NOT NULL DEFAULT 0,
    active_loans_count          INT             NOT NULL DEFAULT 0,
    overdue_loans_count         INT             NOT NULL DEFAULT 0,
    defaulted_loans_count       INT             NOT NULL DEFAULT 0,
    fully_paid_loans_count      INT             NOT NULL DEFAULT 0,

    -- Revenue tracking
    projected_revenue           DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    realised_revenue            DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    revenue_at_risk             DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_losses                DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_interest_foregone     DECIMAL(15,2)   NOT NULL DEFAULT 0.00,

    -- Performance metrics
    default_rate                DECIMAL(5,2)    NOT NULL DEFAULT 0.00,
    collection_rate             DECIMAL(5,2)    NOT NULL DEFAULT 0.00,

    -- Audit
    created_at                  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by                  VARCHAR(20)     NOT NULL,
    last_updated                DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT pk_loan_account  PRIMARY KEY (loan_account_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_loan_account_master   → company_account(master_account_id)
    -- fk_loan_account_officer  → staff_account(staff_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;