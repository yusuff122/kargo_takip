package com.yusuf.kargotakip.business.factory;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.ExpressCargo;

@Component
public class ExpressCargoFactory extends AbstractCargoFactory implements CargoFactory {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.EXPRESS;
    }

    @Override
    public Cargo create(CreateCargoRequest createCargoRequest) {
        return ExpressCargo.builder()
                .senderName(createCargoRequest.getSenderName())
                .receiverName(createCargoRequest.getReceiverName())
                .trackingNumber(generateTrackingNumber())
                .status(CargoStatus.HAZIRLANIYOR)
                .cargoType(CargoType.EXPRESS)
                .shippingCost(new BigDecimal("89.90"))
                .priorityDelivery(true)
                .specialHandling(false)
                .description("Hızlı teslimat - daha pahalı ve öncelikli taşıma")
                .build();
    }
}
