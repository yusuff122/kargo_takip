package com.yusuf.kargotakip.business.decorators;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.responses.CargoCardResponse;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Component
public class SpecialCargoCardDecorator implements CargoCardDecorator {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.SPECIAL;
    }

    @Override
    public CargoCardResponse decorate(CargoCardResponse cargoCardResponse) {
        cargoCardResponse.setBadgeLabel("Özel Koruma");
        cargoCardResponse.setBadgeColor("#7c3aed");
        cargoCardResponse.setDeliveryNote("Kırılabilir ve değerli ürün prosedürü uygulanıyor.");
        cargoCardResponse.setDecoratorTitle("Özel koruma teması uygulandı");
        cargoCardResponse.setCardGradient("linear-gradient(135deg, #f5f3ff 0%, #ede9fe 52%, #ffffff 100%)");
        cargoCardResponse.setBorderColor("#c4b5fd");
        cargoCardResponse.setAccentColor("#7c3aed");
        cargoCardResponse.setNoteBackground("#ede9fe");
        cargoCardResponse.setNoteTextColor("#6d28d9");
        return cargoCardResponse;
    }
}
