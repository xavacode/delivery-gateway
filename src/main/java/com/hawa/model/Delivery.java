package com.hawa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    private long id;

    private String status;

    @Column(name="client_reference")
    private String clientReference;

    @Column(name="tracking_url")
    private String trackingUrl;

    @Column(name="pickup_address")
    private String pickupAddress;

    @Column(name="pickup_contact_name")
    private String pickupContactName;

    @Column(name="pickup_contact_number")
    private String pickupContactNumber;

    @Column(name="dropoff_address")
    private String dropoffAddress;

    @Column(name="dropoff_contact_name")
    private String dropoffContactName;

    @Column(name="dropoff_contact_number")
    private String dropoffContactNumber;

    @Column(name="client_tracking_url")
    private String clientTrackingUrl;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

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
}
