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
public class CargoCardResponse {

    private String orderNumber;
    private String trackingNumber;
    private String status;
    private String customerFullName;
    private String customerEmail;
    private String storeEmployeeFullName;
    private String storeName;
    private String cargoType;
    private BigDecimal shippingCost;
    private boolean priorityDelivery;
    private boolean specialHandling;
    private String description;
    private String badgeLabel;
    private String badgeColor;
    private String statusDisplayText;
    private String deliveryNote;
    private String cardGradient;
    private String borderColor;
    private String accentColor;
    private String noteBackground;
    private String noteTextColor;
    private String decoratorTitle;
}
