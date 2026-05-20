package com.yusuf.kargotakip.business.patterns.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.entities.concretes.CargoType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShippingCostStrategyResolver {

    private final List<ShippingCostStrategy> strategies;

    public ShippingCostStrategy resolve(CargoType cargoType) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(cargoType))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Desteklenmeyen kargo tipi için ücret hesaplanamadı."));
    }
}
