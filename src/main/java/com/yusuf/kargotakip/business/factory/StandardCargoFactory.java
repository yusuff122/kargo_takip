package com.yusuf.kargotakip.business.factory;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.StandardCargo;

@Component
public class StandardCargoFactory extends AbstractCargoFactory implements CargoFactory {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.STANDARD;
    }

    @Override
    public Cargo create(CreateCargoRequest createCargoRequest) {
        return StandardCargo.builder()
                .trackingNumber(generateTrackingNumber())
                .orderNumber(generateOrderNumber())
                .status(CargoStatus.HAZIRLANIYOR)
                .distanceKm(createCargoRequest.getDistanceKm())
                .cargoType(CargoType.STANDARD)
                .shippingCost(BigDecimal.ZERO)
                .priorityDelivery(false)
                .specialHandling(false)
                .description("Normal teslimat - en ucuz ve varsayılan kargo tipi")
                .notificationEmail(createCargoRequest.getCustomerEmail())
                .build();
    }
}
