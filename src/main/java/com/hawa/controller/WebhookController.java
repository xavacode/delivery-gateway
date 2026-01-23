package com.hawa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawa.dto.webhook.stuart.WebhookDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification Handler")
@Slf4j
@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final ObjectMapper objectMapper;

    @PostMapping("/stuart/notifications")
    public ResponseEntity<String> receiveNotifications(@RequestBody WebhookDto webhookDto) throws JsonProcessingException {
        log.info("Webhook Received: {}",objectMapper.writeValueAsString(webhookDto));
        return ResponseEntity.ok("OK");
    }
}
