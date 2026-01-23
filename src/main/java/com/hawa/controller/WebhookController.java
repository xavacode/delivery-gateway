package com.hawa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawa.dto.webhook.StuartWebhookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final ObjectMapper objectMapper;

    @PostMapping("/stuart/notifications")
    public ResponseEntity<String> receiveNotifications(@RequestBody StuartWebhookDto stuartWebhookDto) throws JsonProcessingException {
        log.info("Webhook Received: {}",objectMapper.writeValueAsString(stuartWebhookDto));
        return ResponseEntity.ok("OK");
    }
}
