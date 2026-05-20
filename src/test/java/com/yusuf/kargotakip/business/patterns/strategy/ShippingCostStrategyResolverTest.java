package com.yusuf.kargotakip.business.patterns.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.Customer;
import com.yusuf.kargotakip.entities.concretes.SpecialCargo;
import com.yusuf.kargotakip.entities.concretes.StandardCargo;
import com.yusuf.kargotakip.entities.concretes.StoreEmployee;

class ShippingCostStrategyResolverTest {

    private ShippingCostStrategyResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new ShippingCostStrategyResolver(List.of(
                new StandardShippingCostStrategy(),
                new ExpressShippingCostStrategy(),
                new SpecialShippingCostStrategy()));
    }

    @Test
    void shouldReturnStandardStrategy() {
        assertInstanceOf(StandardShippingCostStrategy.class, resolver.resolve(CargoType.STANDARD));
    }

    @Test
    void shouldReturnExpressStrategy() {
        assertInstanceOf(ExpressShippingCostStrategy.class, resolver.resolve(CargoType.EXPRESS));
    }

    @Test
    void shouldReturnSpecialStrategy() {
        assertInstanceOf(SpecialShippingCostStrategy.class, resolver.resolve(CargoType.SPECIAL));
    }

    @Test
    void standardStrategyShouldCalculateDistanceBasedCost() {
        StandardCargo cargo = StandardCargo.builder()
                .trackingNumber("KRG-STD1")
                .orderNumber("SIP-STD1")
                .status(CargoStatus.HAZIRLANIYOR)
                .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                .storeEmployee(StoreEmployee.builder().firstName("Mert").lastName("Kaya").email("mert@techmarket.com").storeName("TechMarket").build())
                .notificationEmail("ayse@example.com")
                .distanceKm(256)
                .cargoType(CargoType.STANDARD)
                .shippingCost(BigDecimal.ZERO)
                .priorityDelivery(false)
                .specialHandling(false)
                .description("Standart")
                .build();

        BigDecimal cost = resolver.resolve(CargoType.STANDARD).calculate(cargo);

        assertEquals(new BigDecimal("120"), cost);
    }

    @Test
    void specialStrategyShouldIgnoreRemainderAfterHundreds() {
        SpecialCargo cargo = SpecialCargo.builder()
                .trackingNumber("KRG-SP1")
                .orderNumber("SIP-SP1")
                .status(CargoStatus.HAZIRLANIYOR)
                .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                .storeEmployee(StoreEmployee.builder().firstName("Selin").lastName("Aras").email("selin@mucevher.com").storeName("Mücevher Dünyası").build())
                .notificationEmail("ayse@example.com")
                .distanceKm(256)
                .cargoType(CargoType.SPECIAL)
                .shippingCost(BigDecimal.ZERO)
                .priorityDelivery(false)
                .specialHandling(true)
                .description("Özel")
                .build();

        BigDecimal cost = resolver.resolve(CargoType.SPECIAL).calculate(cargo);

        assertEquals(new BigDecimal("230"), cost);
    }

    @Test
    void shouldThrowExceptionWhenStrategyIsNotSupported() throws Exception {
        ShippingCostStrategyResolver emptyResolver = new ShippingCostStrategyResolver(List.of());

        assertThrows(BusinessException.class, () -> emptyResolver.resolve(CargoType.STANDARD));
    }
}
