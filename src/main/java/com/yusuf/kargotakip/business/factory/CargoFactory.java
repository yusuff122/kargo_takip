package com.yusuf.kargotakip.business.factory;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.entities.concretes.Cargo;

public interface CargoFactory {

    Cargo create(CreateCargoRequest createCargoRequest);
}
