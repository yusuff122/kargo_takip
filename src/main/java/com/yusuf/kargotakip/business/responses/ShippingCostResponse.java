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
public class ShippingCostResponse {

    private String trackingNumber;
    private BigDecimal cost;
}
