-- =============================================================
-- V11__add_sync_columns.sql
-- Shield Point Capital — Loan Record Management System
-- Adds offline sync columns to all syncable tables
-- Required for WatermelonDB sync with React Native frontend
-- =============================================================

-- staff_account
ALTER TABLE staff_account
    ADD COLUMN device_id        VARCHAR(100)    NULL,
    ADD COLUMN sync_status      ENUM(
                                    'SYNCED',
                                    'PENDING',
                                    'CONFLICT'
                                )               NOT NULL DEFAULT 'SYNCED',
    ADD COLUMN synced_at        DATETIME        NULL;

-- loan_account
ALTER TABLE loan_account
    ADD COLUMN device_id        VARCHAR(100)    NULL,
    ADD COLUMN sync_status      ENUM(
                                    'SYNCED',
                                    'PENDING',
                                    'CONFLICT'
                                )               NOT NULL DEFAULT 'SYNCED',
    ADD COLUMN synced_at        DATETIME        NULL;

-- user_info
ALTER TABLE user_info
    ADD COLUMN device_id        VARCHAR(100)    NULL,
    ADD COLUMN sync_status      ENUM(
                                    'SYNCED',
                                    'PENDING',
                                    'CONFLICT'
                                )               NOT NULL DEFAULT 'SYNCED',
    ADD COLUMN synced_at        DATETIME        NULL;

-- loan_tracking
ALTER TABLE loan_tracking
    ADD COLUMN device_id        VARCHAR(100)    NULL,
    ADD COLUMN sync_status      ENUM(
                                    'SYNCED',
                                    'PENDING',
                                    'CONFLICT'
                                )               NOT NULL DEFAULT 'SYNCED',
    ADD COLUMN synced_at        DATETIME        NULL;

-- payment_log
ALTER TABLE payment_log
    ADD COLUMN device_id        VARCHAR(100)    NULL,
    ADD COLUMN sync_status      ENUM(
                                    'SYNCED',
                                    'PENDING',
                                    'CONFLICT'
                                )               NOT NULL DEFAULT 'SYNCED',
    ADD COLUMN synced_at        DATETIME        NULL;