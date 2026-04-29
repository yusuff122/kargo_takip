package com.yusuf.kargotakip.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.yusuf.kargotakip.business.factory.CargoFactoryResolver;
import com.yusuf.kargotakip.business.factory.ExpressCargoFactory;
import com.yusuf.kargotakip.business.factory.SpecialCargoFactory;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.ExpressCargo;
import com.yusuf.kargotakip.business.factory.StandardCargoFactory;

class CargoManagerTest {

    private CargoRepository cargoRepository;
    private CargoManager cargoManager;

    @BeforeEach
    void setUp() {
        cargoRepository = Mockito.mock(CargoRepository.class);
        CargoBusinessRules cargoBusinessRules = new CargoBusinessRules(cargoRepository);
        CargoFactoryResolver cargoFactoryResolver = new CargoFactoryResolver(
                java.util.List.of(
                        new StandardCargoFactory(),
                        new ExpressCargoFactory(),
                        new SpecialCargoFactory()));
        cargoManager = new CargoManager(cargoRepository, cargoFactoryResolver, cargoBusinessRules);
    }

    @Test
    void shouldCreateStandardCargoWithTrackingNumber() {
        CreateCargoRequest request = new CreateCargoRequest("Yusuf Magaza", "Ayse Yilmaz", CargoType.STANDARD);
        when(cargoRepository.save(any(Cargo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GetCargoByTrackingNumberResponse response = cargoManager.add(request);

        assertNotNull(response.getTrackingNumber());
        assertEquals("HAZIRLANIYOR", response.getStatus());
        assertEquals("Yusuf Magaza", response.getSenderName());
        assertEquals("Ayse Yilmaz", response.getReceiverName());
        assertEquals("STANDARD", response.getCargoType());
        assertEquals(new BigDecimal("49.90"), response.getShippingCost());
    }

    @Test
    void shouldCreateExpressCargoWithPriorityDelivery() {
        CreateCargoRequest request = new CreateCargoRequest("Hizli Satici", "Acil Musteri", CargoType.EXPRESS);
        when(cargoRepository.save(any(Cargo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GetCargoByTrackingNumberResponse response = cargoManager.add(request);

        assertEquals("EXPRESS", response.getCargoType());
        assertEquals(new BigDecimal("89.90"), response.getShippingCost());
        assertEquals(true, response.isPriorityDelivery());
        assertEquals(false, response.isSpecialHandling());
    }

    @Test
    void shouldReturnCargoWhenTrackingNumberExists() {
        Cargo cargo = ExpressCargo.builder()
                .id(1L)
                .trackingNumber("KRG-TEST123")
                .status(CargoStatus.HAZIRLANIYOR)
                .senderName("Satici")
                .receiverName("Musteri")
                .cargoType(CargoType.EXPRESS)
                .shippingCost(new BigDecimal("89.90"))
                .priorityDelivery(true)
                .specialHandling(false)
                .description("Hizli teslimat - daha pahali ve oncelikli tasima")
                .build();
        when(cargoRepository.findByTrackingNumber("KRG-TEST123")).thenReturn(Optional.of(cargo));

        GetCargoByTrackingNumberResponse foundCargo =
                cargoManager.getByTrackingNumber("KRG-TEST123");

        assertEquals("KRG-TEST123", foundCargo.getTrackingNumber());
        assertEquals("HAZIRLANIYOR", foundCargo.getStatus());
        assertEquals("EXPRESS", foundCargo.getCargoType());
    }

    @Test
    void shouldThrowExceptionWhenTrackingNumberDoesNotExist() {
        when(cargoRepository.findByTrackingNumber("KRG-UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> cargoManager.getByTrackingNumber("KRG-UNKNOWN"));
    }
}
