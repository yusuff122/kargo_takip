package com.yusuf.kargotakip.business.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.entities.concretes.CargoType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CargoFactoryResolver {

    private final List<CargoFactory> cargoFactories;

    public CargoFactory resolve(CargoType cargoType) {
        return cargoFactories.stream()
                .filter(factory -> factory.supports(cargoType))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Desteklenmeyen kargo tipi: " + cargoType));
    }
}
