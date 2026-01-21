CREATE TABLE job (
    id BIGINT PRIMARY KEY,
    status VARCHAR(45) NOT NULL,
    package_type VARCHAR(30),
    timezone_id VARCHAR(50),
    offset_minutes INT,
    created_at_utc DATETIME(6),
    created_at_iso VARCHAR(35),
    pickup_at_utc DATETIME(6),
    pickup_at_iso VARCHAR(35),
    dropoff_at_utc DATETIME(6),
    dropoff_at_iso VARCHAR(35),
    ended_at_utc DATETIME(6),
    ended_at_iso VARCHAR(35),
    request_payload TEXT,
    response_payload TEXT,
    INDEX idx_created_at_utc (created_at_utc),
    INDEX idx_created_at_prefix (created_at_iso(20)),
    INDEX idx_offset_minutes (offset_minutes),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

