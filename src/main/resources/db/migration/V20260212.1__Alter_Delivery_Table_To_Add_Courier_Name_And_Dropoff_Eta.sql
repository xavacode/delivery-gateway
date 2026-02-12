ALTER TABLE delivery
ADD (
    courier_name VARCHAR(120),
    offset_minutes INT,
    dropoff_eta_utc DATETIME(6),
    dropoff_eta_iso VARCHAR(35),
    timezone_id VARCHAR(50)
);