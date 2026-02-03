package com.hawa.model;

import com.hawa.annotation.SnowflakeId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "delivery_log")
public class DeliveryLog {

    @Id
    @SnowflakeId
    private long id;

    private String status;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "created_at")
    LocalDateTime createdAt;

}
