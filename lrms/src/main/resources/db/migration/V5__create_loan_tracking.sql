-- Creates the loan_tracking table
-- One record per loan disbursement per borrower
-- Multiple records per borrower allowed — one ACTIVE at a time
-- enforced at application layer, not database level
-- References: user_info(id_number), loan_account(loan_account_id)
-- Referenced by: payment_log, loan_status_history
-- NOTE: FKs added in V10__add_foreign_keys.sql

CREATE TABLE loan_tracking (

    -- Primary key
    tracking_id             BIGINT          NOT NULL AUTO_INCREMENT,

    -- Borrower and account links
    id_number               VARCHAR(20)     NOT NULL,
    loan_account_id         VARCHAR(20)     NOT NULL,

    -- Loan details at disbursement (immutable after creation)
    loan_type               VARCHAR(50)     NOT NULL,
    disbursement_date       DATE            NOT NULL,
    disbursement_amount     DECIMAL(15,2)   NOT NULL,
    maturity_date           DATE            NOT NULL,
    total_repayment_amount  DECIMAL(15,2)   NOT NULL,

    -- Repayment tracking
    outstanding_balance     DECIMAL(15,2)   NOT NULL,
    amount_paid             DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    payment_date            DATE            NULL,
    payment_method          VARCHAR(30)     NULL,
    payment_reference       VARCHAR(100)    NULL,

    -- Loan state
    loan_status             ENUM(
                                'ACTIVE',
                                'PAID',
                                'DEFAULTED',
                                'WRITTEN_OFF',
                                'RESTRUCTURED'
                            )               NOT NULL DEFAULT 'ACTIVE',
    days_past_due           INT             NOT NULL DEFAULT 0,
    penalty_accrued         DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_amount_due        DECIMAL(15,2)   NOT NULL,

    -- Audit
    last_updated            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by              VARCHAR(20)     NOT NULL,
    remarks                 TEXT            NULL,

    -- Constraints
    CONSTRAINT pk_loan_tracking PRIMARY KEY (tracking_id)

    -- NOTE: FK constraints added in V10__add_foreign_keys.sql:
    -- fk_loan_tracking_borrower → user_info(id_number)
    -- fk_loan_tracking_account  → loan_account(loan_account_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;