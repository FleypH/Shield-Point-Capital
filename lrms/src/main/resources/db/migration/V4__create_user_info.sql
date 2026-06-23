-- =============================================================
-- V4__create_user_info.sql
-- Shield Point Capital — Loan Record Management System
-- Creates the user_info (borrower identity) table
-- Stores borrower personal details only — no loan data
-- Loan data lives in loan_tracking (V5)
-- Referenced by: loan_tracking
-- =============================================================

CREATE TABLE user_info (

    -- Primary identification
    id_number       VARCHAR(255)     NOT NULL,
    first_name       VARCHAR(100)    NOT NULL,
    last_name       VARCHAR(100)    NOT NULL,
    phone           VARCHAR(255)     NULL,
    address         TEXT            NULL,

    -- Audit
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by      VARCHAR(20)     NOT NULL,

    -- Constraints
    CONSTRAINT pk_user_info PRIMARY KEY (id_number)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;