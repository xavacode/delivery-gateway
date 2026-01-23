package com.hawa.dto.webhook.stuart;

import lombok.Data;

@Data
public class Cancellation {

    private String actor;
    private String reason;
    private String key;
    private String comment;

}
