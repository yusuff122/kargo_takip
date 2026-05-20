package com.yusuf.kargotakip.business.patterns.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Component
public class ExpressShippingCostStrategy implements ShippingCostStrategy {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.EXPRESS;
    }

    @Override
    public BigDecimal calculate(Cargo cargo) {
        int hundredKmBlocks = cargo.getDistanceKm() / 100;
        return new BigDecimal("150").add(new BigDecimal(hundredKmBlocks * 20));
    }
}
