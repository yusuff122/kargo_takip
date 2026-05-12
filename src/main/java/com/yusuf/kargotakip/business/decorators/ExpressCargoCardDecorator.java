package com.yusuf.kargotakip.business.decorators;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.responses.CargoCardResponse;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Component
public class ExpressCargoCardDecorator implements CargoCardDecorator {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.EXPRESS;
    }

    @Override
    public CargoCardResponse decorate(CargoCardResponse cargoCardResponse) {
        cargoCardResponse.setBadgeLabel("Hızlı Teslimat");
        cargoCardResponse.setBadgeColor("#ea580c");
        cargoCardResponse.setDeliveryNote("Öncelikli taşıma hattında ilerliyor.");
        cargoCardResponse.setDecoratorTitle("Hızlı teslimat teması uygulandı");
        cargoCardResponse.setCardGradient("linear-gradient(135deg, #fff7ed 0%, #ffedd5 52%, #ffffff 100%)");
        cargoCardResponse.setBorderColor("#fdba74");
        cargoCardResponse.setAccentColor("#ea580c");
        cargoCardResponse.setNoteBackground("#ffedd5");
        cargoCardResponse.setNoteTextColor("#c2410c");
        return cargoCardResponse;
    }
}
