package com.yusuf.kargotakip.business.patterns.strategy;

import java.math.BigDecimal;

import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoType;

public interface ShippingCostStrategy {

    boolean supports(CargoType cargoType);

    BigDecimal calculate(Cargo cargo);
}
