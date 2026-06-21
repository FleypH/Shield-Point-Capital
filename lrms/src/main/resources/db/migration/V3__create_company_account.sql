-- Creates the company_account table and inserts the single
-- company record immediately.
-- Referenced by: loan_account(master_account_id)

CREATE TABLE company_account (

    -- Primary identification
    account_id                  TINYINT         NOT NULL,
    company_name                VARCHAR(100)    NOT NULL,

    -- Capital tracking
    total_opening_capital       DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_allocated_capital     DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    unallocated_capital         DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    current_capital             DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    capital_deployed            DECIMAL(15,2)   NOT NULL DEFAULT 0.00,

    -- Loan counts
    total_loans_disbursed       INT             NOT NULL DEFAULT 0,
    total_disbursed_amount      DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    active_loans_count          INT             NOT NULL DEFAULT 0,
    overdue_loans_count         INT             NOT NULL DEFAULT 0,
    defaulted_loans_count       INT             NOT NULL DEFAULT 0,
    fully_paid_loans_count      INT             NOT NULL DEFAULT 0,

    -- Revenue tracking
    projected_revenue           DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    projected_revenue_overdue   DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    realised_revenue            DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    revenue_at_risk             DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_losses                DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    total_interest_foregone     DECIMAL(15,2)   NOT NULL DEFAULT 0.00,

    -- Performance metrics
    collection_rate             DECIMAL(5,2)    NOT NULL DEFAULT 0.00,
    default_rate                DECIMAL(5,2)    NOT NULL DEFAULT 0.00,
    capital_utilisation_rate    DECIMAL(5,2)    NOT NULL DEFAULT 0.00,

    -- Audit
    last_updated                DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT pk_company_account PRIMARY KEY (account_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- Insert the single company record immediately after creation.
-- account_id = 1 always. This row is never deleted.
-- Capital values start at 0.00 — admin sets opening capital
-- through the application after first login.

INSERT INTO company_account (
    account_id,
    company_name,
    total_opening_capital,
    total_allocated_capital,
    unallocated_capital,
    current_capital,
    capital_deployed,
    total_loans_disbursed,
    total_disbursed_amount,
    active_loans_count,
    overdue_loans_count,
    defaulted_loans_count,
    fully_paid_loans_count,
    projected_revenue,
    projected_revenue_overdue,
    realised_revenue,
    revenue_at_risk,
    total_losses,
    total_interest_foregone,
    collection_rate,
    default_rate,
    capital_utilisation_rate
) VALUES (
    1,
    'Shield Point Capital',
    0.00,
    0.00,
    0.00,
    0.00,
    0.00,
    0,
    0.00,
    0,
    0,
    0,
    0,
    0.00,
    0.00,
    0.00,
    0.00,
    0.00,
    0.00,
    0.00,
    0.00,
    0.00
);