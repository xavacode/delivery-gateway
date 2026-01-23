package com.hawa.dto.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StuartAccount {

    @JsonProperty("id")
    private Long id;  // 451871
}