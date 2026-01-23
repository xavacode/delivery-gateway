package com.hawa.dto.webhook.stuart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Return {

    private String actor;
    private String key;
    private String reason;
}