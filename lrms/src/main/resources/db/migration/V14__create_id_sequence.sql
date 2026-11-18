CREATE TABLE id_sequence (
    seq_name VARCHAR(50) NOT NULL PRIMARY KEY,
    next_val BIGINT      NOT NULL
);

INSERT INTO id_sequence (seq_name, next_val)
SELECT 'STAFF_ID', COUNT(*) + 1 FROM staff_account;