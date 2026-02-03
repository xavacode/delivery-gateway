CREATE TABLE delivery_log (
    id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME(6)
    delivery_id BIGINT,
    PRIMARY KEY (id),
    INDEX idx_status (status),
    INDEX idx_delivery_id (delivery_id)
    CONSTRAINT fk_delivery
        FOREIGN KEY (delivery_id)
        REFERENCES delivery(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;