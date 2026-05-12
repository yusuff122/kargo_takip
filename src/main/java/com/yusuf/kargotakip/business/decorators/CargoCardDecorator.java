package com.yusuf.kargotakip.business.decorators;

import com.yusuf.kargotakip.business.responses.CargoCardResponse;
import com.yusuf.kargotakip.entities.concretes.CargoType;

public interface CargoCardDecorator {

    boolean supports(CargoType cargoType);

    CargoCardResponse decorate(CargoCardResponse cargoCardResponse);
}
