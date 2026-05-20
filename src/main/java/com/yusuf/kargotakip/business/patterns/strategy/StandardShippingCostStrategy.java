package com.yusuf.kargotakip.business.patterns.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Component
public class StandardShippingCostStrategy implements ShippingCostStrategy {

    @Override
    public boolean supports(CargoType cargoType) {
        return cargoType == CargoType.STANDARD;
    }

    @Override
    public BigDecimal calculate(Cargo cargo) {
        int hundredKmBlocks = cargo.getDistanceKm() / 100;
        return new BigDecimal("100").add(new BigDecimal(hundredKmBlocks * 10));
    }
}
