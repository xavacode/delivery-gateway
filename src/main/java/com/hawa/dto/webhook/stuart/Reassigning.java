package com.hawa.dto.webhook.stuart;

import lombok.Data;

@Data
public class Reassigning {

    private String actor;
    private String key;
    private String reason;
}