package com.yusuf.kargotakip.business.responses;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCargoByTrackingNumberResponse {

    private String orderNumber;
    private String trackingNumber;
    private String status;
    private String customerFullName;
    private String customerEmail;
    private String storeEmployeeFullName;
    private String storeEmployeeEmail;
    private String storeName;
    private int distanceKm;
    private String cargoType;
    private BigDecimal shippingCost;
    private boolean priorityDelivery;
    private boolean specialHandling;
    private String description;
    private String notificationMessage;
}
