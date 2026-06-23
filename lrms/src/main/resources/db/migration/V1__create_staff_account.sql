-- Creates the staff_account table
-- Referenced by: loan_account, staff_audit_log, loan_status_history
-- NOTE: FK on loan_account_id added in V10__add_foreign_keys.sql
CREATE TABLE staff_account (
    -- Primary identification
    staff_id VARCHAR(20) NOT NULL,
    national_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    address TEXT NULL,

    -- Contact & login
    email VARCHAR(150) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    job_title VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'LOAN_OFFICER', 'ACCOUNTANT') NOT NULL,

    -- Assignment
    loan_account_id VARCHAR(20) NULL,

    -- Security
    password_hash VARCHAR(255) NOT NULL,
    password_reset_token VARCHAR(100) NULL,
    token_expires_at DATETIME NULL,
    token_version INT NOT NULL DEFAULT 0,

    -- Account status
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    failed_login_count TINYINT NOT NULL DEFAULT 0,
    locked_until DATETIME NULL,

    -- Activity tracking
    last_login_at DATETIME NULL,
    last_login_ip VARCHAR(45) NULL,

    -- Audit
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(20) NULL,

    -- Constraints
    CONSTRAINT pk_staff_account       PRIMARY KEY  (staff_id),
    CONSTRAINT uq_staff_national_id   UNIQUE       (national_id),
    CONSTRAINT uq_staff_email         UNIQUE       (email),
    CONSTRAINT uq_staff_phone         UNIQUE       (phone_number)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;