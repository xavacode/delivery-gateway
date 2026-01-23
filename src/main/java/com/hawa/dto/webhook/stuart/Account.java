package com.hawa.dto.webhook.stuart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {

    @JsonProperty("id")
    private Long id;  // 451871
}