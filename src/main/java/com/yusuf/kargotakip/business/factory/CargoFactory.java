package com.yusuf.kargotakip.business.factory;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoType;

public interface CargoFactory {

    boolean supports(CargoType cargoType);

    Cargo create(CreateCargoRequest createCargoRequest);
}
