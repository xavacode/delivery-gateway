package com.hawa.dto.webhook.stuart;

import lombok.Data;

@Data
public class ProximityRadius {

    private Integer magnitude;
    private Integer value;
    private String unit;
}