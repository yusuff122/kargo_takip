package com.yusuf.kargotakip.business.decorators;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.responses.CargoCardResponse;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Component
public class StandardCargoCardDecorator implements CargoCardDecorator {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.STANDARD;
    }

    @Override
    public CargoCardResponse decorate(CargoCardResponse cargoCardResponse) {
        cargoCardResponse.setBadgeLabel("Standart Teslimat");
        cargoCardResponse.setBadgeColor("#2563eb");
        cargoCardResponse.setDeliveryNote("Ekonomik gönderi planı ile taşınıyor.");
        cargoCardResponse.setDecoratorTitle("Standart gönderi teması uygulandı");
        cargoCardResponse.setCardGradient("linear-gradient(135deg, #eff6ff 0%, #dbeafe 55%, #ffffff 100%)");
        cargoCardResponse.setBorderColor("#93c5fd");
        cargoCardResponse.setAccentColor("#2563eb");
        cargoCardResponse.setNoteBackground("#dbeafe");
        cargoCardResponse.setNoteTextColor("#1d4ed8");
        return cargoCardResponse;
    }
}
