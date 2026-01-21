CREATE TABLE delivery (
    id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    client_reference VARCHAR(100),
    tracking_url VARCHAR(500),
    pickup_address TEXT,
    pickup_contact_name VARCHAR(100),
    pickup_contact_number VARCHAR(50),
    dropoff_address TEXT,
    dropoff_contact_name VARCHAR(100),
    dropoff_contact_number VARCHAR(50),
    client_tracking_url VARCHAR(500),
    job_id BIGINT,
    PRIMARY KEY (id),
    INDEX idx_status (status),
    INDEX idx_client_reference (client_reference),
    INDEX idx_job_id (job_id),
    INDEX idx_tracking_url (tracking_url(100)),
    CONSTRAINT fk_delivery_job
        FOREIGN KEY (job_id)
        REFERENCES job(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;