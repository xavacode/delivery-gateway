package com.hawa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {

    @Id
    private long id;

    private String status;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "offset_minutes")
    private Integer offsetMinutes;

    @Column(name = "created_at_iso")
    private String createdAtIso;

    @Column(name = "created_at_utc")
    private LocalDateTime createdAtUtc;

    @Column(name = "pickup_at_iso")
    private String pickupAtIso;

    @Column(name = "pickup_at_utc")
    private LocalDateTime pickupAtUtc;

    @Column(name = "dropoff_at_iso")
    private String dropoffAtIso;

    @Column(name = "dropoff_at_utc")
    private LocalDateTime dropoffAtUtc;

    @Column(name = "ended_at_iso")
    private String endedAtIso;

    @Column(name = "ended_at_utc")
    private LocalDateTime endedAtUtc;

    @Column(name = "timezone_id")
    private String timezoneId;

    @Column(name = "request_payload")
    private String requestPayload;

    @Column(name = "response_payload")
    private String responsePayload;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Delivery> deliveries = new ArrayList<>();

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
        delivery.setJob(this);
    }

    public void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
        delivery.setJob(null);
    }

    public OffsetDateTime getCreatedAt() {
        if (createdAtIso != null) {
            return OffsetDateTime.parse(createdAtIso);
        }
        return null;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        if (createdAt != null) {
            // Store ISO string for exact reconstruction
            this.createdAtIso = createdAt.toString();

            // Store UTC for querying
            this.createdAtUtc = createdAt.withOffsetSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();

            // Store offset
            this.offsetMinutes = createdAt.getOffset().getTotalSeconds() / 60;

            // Try to get timezone name
            try {
                ZoneId zoneId = createdAt.getOffset().normalized();
                if (zoneId instanceof ZoneOffset) {
                    this.timezoneId = createdAt.getOffset().getId();
                } else {
                    this.timezoneId = zoneId.getId();
                }
            } catch (Exception e) {
                this.timezoneId = createdAt.getOffset().getId();
            }
        }
    }

    public OffsetDateTime getPickupAt() {
        if (pickupAtIso != null) {
            return OffsetDateTime.parse(pickupAtIso);
        }
        return null;
    }

    public void setPickupAt(OffsetDateTime pickupAt) {
        if (pickupAt != null) {
            // Store ISO string for exact reconstruction
            this.pickupAtIso = pickupAt.toString();

            // Store UTC for querying
            this.pickupAtUtc = pickupAt.withOffsetSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();
        }
    }

    public OffsetDateTime getDropoffAt() {
        if (dropoffAtIso != null) {
            return OffsetDateTime.parse(dropoffAtIso);
        }
        return null;
    }

    public void setDropoffAt(OffsetDateTime dropoffAt) {
        if (dropoffAt != null) {
            // Store ISO string for exact reconstruction
            this.dropoffAtIso = dropoffAt.toString();

            // Store UTC for querying
            this.dropoffAtUtc = dropoffAt.withOffsetSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();
        }
    }

    public OffsetDateTime getEndedAt() {
        if (endedAtIso != null) {
            return OffsetDateTime.parse(endedAtIso);
        }
        return null;
    }

    public void setEndedAt(OffsetDateTime endedAt) {
        if (endedAt != null) {
            // Store ISO string for exact reconstruction
            this.endedAtIso = endedAt.toString();

            // Store UTC for querying
            this.endedAtUtc = endedAt.withOffsetSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();
        }
    }

}
