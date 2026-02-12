package com.hawa.dto.tracking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Builder
@Data
public class TrackingDto {

    private long deliveryId;
    private String storeName;
    private String customerName;
    private String courierName;
    private String shippingService;
    private OffsetDateTime dropoffEta;
    private String currentStatus;
    private String statusColor;
    private Map<String, DelieryStatus> statusMap;

    public record DelieryStatus(String status, LocalDateTime dateTime) {
    }
}
