package com.hawa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    private long id;

    private String status;

    @Column(name = "client_reference")
    private String clientReference;

    @Column(name = "tracking_url")
    private String trackingUrl;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "pickup_contact_name")
    private String pickupContactName;

    @Column(name = "pickup_contact_number")
    private String pickupContactNumber;

    @Column(name = "dropoff_address")
    private String dropoffAddress;

    @Column(name = "dropoff_contact_name")
    private String dropoffContactName;

    @Column(name = "dropoff_contact_number")
    private String dropoffContactNumber;

    @Column(name = "client_tracking_url")
    private String clientTrackingUrl;

    @Column(name="courier_name")
    private String courierName;

    @Column(name = "offset_minutes")
    private Integer offsetMinutes;

    @Column(name = "dropoff_eta_iso")
    private String dropOffEtaIso;

    @Column(name = "dropoff_eta_utc")
    private LocalDateTime dropOffEtaUtc;

    @Column(name = "timezone_id")
    private String timezoneId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "delivery")
    private List<DeliveryLog> deliveryLogs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public OffsetDateTime getDropOffEta() {
        if (dropOffEtaIso != null) {
            return OffsetDateTime.parse(dropOffEtaIso);
        }
        return null;
    }

    public void setDropOffEta(OffsetDateTime dropOffEta) {
        if (dropOffEta != null) {
            // Store ISO string for exact reconstruction
            this.dropOffEtaIso = dropOffEta.toString();

            // Store UTC for querying
            this.dropOffEtaUtc = dropOffEta.withOffsetSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();

            // Store offset
            this.offsetMinutes = dropOffEta.getOffset().getTotalSeconds() / 60;

            // Try to get timezone name
            try {
                ZoneId zoneId = dropOffEta.getOffset().normalized();
                if (zoneId instanceof ZoneOffset) {
                    this.timezoneId = dropOffEta.getOffset().getId();
                } else {
                    this.timezoneId = zoneId.getId();
                }
            } catch (Exception e) {
                this.timezoneId = dropOffEta.getOffset().getId();
            }
        }
    }
}
