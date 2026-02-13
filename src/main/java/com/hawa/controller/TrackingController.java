package com.hawa.controller;

import com.hawa.dto.tracking.TrackingDto;
import com.hawa.model.Delivery;
import com.hawa.model.DeliveryLog;
import com.hawa.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/trackings")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping("/{trackingId}")
    public String showDeliveryProgress(@PathVariable long trackingId, Model model) {
        Delivery delivery = trackingService.getDelivery(trackingId);
        TrackingDto trackingDto = TrackingDto.builder()
                .deliveryId(trackingId)
                .shippingService("BaitDash (from 20min Delivery)")
                .customerName(delivery.getDropoffContactName())
                .dropoffEta(delivery.getDropOffEta())
                .storeName(delivery.getJob().getStore().getName())
                .courierName(delivery.getCourierName()).build();
        Map<String, TrackingDto.DelieryStatus> statusMap=new HashMap<>();
        for (DeliveryLog deliveryLog : delivery.getDeliveryLogs()) {
            if(deliveryLog.getStatus().equals("courier_arriving")){
                if(statusMap.containsKey("COURIER_ARRIVING_FOR_PICKUP")){
                    statusMap.put(deliveryLog.getStatus().toUpperCase()+"_FOR_DROPOFF",new TrackingDto.DelieryStatus("COMPLETED", deliveryLog.getCreatedAt()));
                    trackingDto.setCurrentStatus(deliveryLog.getStatus().toUpperCase()+"_FOR_DROPOFF");
                }else{
                    statusMap.put(deliveryLog.getStatus().toUpperCase()+"_FOR_PICKUP",new TrackingDto.DelieryStatus("COMPLETED", deliveryLog.getCreatedAt()));
                    trackingDto.setCurrentStatus(deliveryLog.getStatus().toUpperCase()+"_FOR_PICKUP");
                }
            }
            statusMap.put(deliveryLog.getStatus().toUpperCase(),new TrackingDto.DelieryStatus("COMPLETED", deliveryLog.getCreatedAt()));
            trackingDto.setCurrentStatus(deliveryLog.getStatus().toUpperCase());
        }
        trackingDto.setStatusMap(statusMap);
        int totalSteps = 7;
        int completedSteps = 0;
        for (TrackingDto.DelieryStatus status : trackingDto.getStatusMap().values()) {
            if ("COMPLETED".equals(status.status())) {
                completedSteps++;
            }
        }
        int progressPercentage = (completedSteps * 100) / totalSteps;

        model.addAttribute("statusMap", trackingDto.getStatusMap());
        model.addAttribute("progressPercentage", progressPercentage);
        model.addAttribute("tracking", trackingDto);
        return "tracking";
    }
}
