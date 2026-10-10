-- =============================================================
-- V12__create_sync_tables.sql
-- Shield Point Capital — Loan Record Management System
-- Creates offline sync management tables
-- =============================================================

-- Tracks every sync operation per device
CREATE TABLE sync_record (

    sync_id             BIGINT          NOT NULL AUTO_INCREMENT,
    device_id           VARCHAR(100)    NOT NULL,
    staff_id            VARCHAR(20)     NOT NULL,
    sync_started_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sync_completed_at   DATETIME        NULL,
    records_pushed      INT             NOT NULL DEFAULT 0,
    records_pulled      INT             NOT NULL DEFAULT 0,
    sync_status         ENUM(
                            'IN_PROGRESS',
                            'COMPLETED',
                            'FAILED'
                        )               NOT NULL DEFAULT 'IN_PROGRESS',
    error_message       TEXT            NULL,

    CONSTRAINT pk_sync_record PRIMARY KEY (sync_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- Tracks active device sessions for offline access control
CREATE TABLE device_session (

    session_id          BIGINT          NOT NULL AUTO_INCREMENT,
    device_id           VARCHAR(100)    NOT NULL,
    staff_id            VARCHAR(20)     NOT NULL,
    device_name         VARCHAR(100)    NULL,
    last_seen_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    registered_at       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_device_session    PRIMARY KEY (session_id),
    CONSTRAINT uq_device_staff      UNIQUE      (device_id, staff_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- Tracks sync conflicts for manual resolution
CREATE TABLE conflict_log (

    conflict_id         BIGINT          NOT NULL AUTO_INCREMENT,
    device_id           VARCHAR(100)    NOT NULL,
    staff_id            VARCHAR(20)     NOT NULL,
    table_name          VARCHAR(50)     NOT NULL,
    record_id           VARCHAR(50)     NOT NULL,
    client_value        TEXT            NOT NULL,
    server_value        TEXT            NOT NULL,
    conflict_detected_at DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at         DATETIME        NULL,
    resolved_by         VARCHAR(20)     NULL,
    resolution          ENUM(
                            'CLIENT_WINS',
                            'SERVER_WINS',
                            'MANUAL'
                        )               NULL,

    CONSTRAINT pk_conflict_log PRIMARY KEY (conflict_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;