package com.yusuf.kargotakip.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yusuf.kargotakip.business.factory.DefaultCargoFactory;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.dataAccess.concretes.InMemoryCargoRepository;

class CargoManagerTest {

    private CargoManager cargoManager;

    @BeforeEach
    void setUp() {
        CargoRepository cargoRepository = new InMemoryCargoRepository();
        CargoBusinessRules cargoBusinessRules = new CargoBusinessRules(cargoRepository);
        cargoManager = new CargoManager(cargoRepository, new DefaultCargoFactory(), cargoBusinessRules);
    }

    @Test
    void shouldCreateCargoWithTrackingNumber() {
        CreateCargoRequest request = new CreateCargoRequest();
        request.setSenderName("Yusuf Magaza");
        request.setReceiverName("Ayse Yilmaz");

        GetCargoByTrackingNumberResponse response = cargoManager.add(request);

        assertNotNull(response.getTrackingNumber());
        assertEquals("HAZIRLANIYOR", response.getStatus());
        assertEquals("Yusuf Magaza", response.getSenderName());
        assertEquals("Ayse Yilmaz", response.getReceiverName());
    }

    @Test
    void shouldReturnCargoWhenTrackingNumberExists() {
        CreateCargoRequest request = new CreateCargoRequest();
        request.setSenderName("Satici");
        request.setReceiverName("Musteri");

        GetCargoByTrackingNumberResponse createdCargo = cargoManager.add(request);

        GetCargoByTrackingNumberResponse foundCargo =
                cargoManager.getByTrackingNumber(createdCargo.getTrackingNumber());

        assertEquals(createdCargo.getTrackingNumber(), foundCargo.getTrackingNumber());
        assertEquals("HAZIRLANIYOR", foundCargo.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenTrackingNumberDoesNotExist() {
        assertThrows(BusinessException.class,
                () -> cargoManager.getByTrackingNumber("KRG-UNKNOWN"));
    }
}
