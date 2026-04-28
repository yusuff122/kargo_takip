package com.yusuf.kargotakip.business.factory;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;

@Component
public class DefaultCargoFactory implements CargoFactory {

    @Override
    public Cargo create(CreateCargoRequest createCargoRequest) {
        Cargo cargo = new Cargo();
        cargo.setSenderName(createCargoRequest.getSenderName());
        cargo.setReceiverName(createCargoRequest.getReceiverName());
        cargo.setTrackingNumber(generateTrackingNumber());
        cargo.setStatus(CargoStatus.HAZIRLANIYOR);
        return cargo;
    }

    private String generateTrackingNumber() {
        return "KRG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
