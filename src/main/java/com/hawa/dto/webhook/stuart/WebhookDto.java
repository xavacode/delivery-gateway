package com.hawa.dto.webhook.stuart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class WebhookDto {

    @JsonProperty("version")
    private String version;  // "v3"

    @JsonProperty("occurred_at")
    private OffsetDateTime occurredAt;  // "2023-02-13T15:29:36.000+01:00"

    @JsonProperty("event_id")
    private UUID eventId;  // "93ce4c52-5b27-4cec-9baf-68cfcdd4da46"

    @JsonProperty("webhook_id")
    private Long webhookId;  // 2359

    @JsonProperty("topic")
    private String topic;  // "package_created"

    @JsonProperty("details")
    private WebhookDetails details;

    public long getPackageId(){
        return Long.parseLong(this.details.getPackageId());
    }
}
