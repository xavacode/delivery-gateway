package com.hawa.model;

import com.hawa.annotation.SnowflakeId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "store")
public class Store {

    @Id
    @SnowflakeId
    private long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;

}
