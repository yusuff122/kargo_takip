package com.yusuf.kargotakip.business.patterns.observer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.yusuf.kargotakip.entities.concretes.Cargo;

class CargoStatusPublisherTest {

    private CargoObserver mailObserver;
    private CargoObserver smsObserver;
    private CargoObserver logObserver;
    private CargoStatusPublisher cargoStatusPublisher;

    @BeforeEach
    void setUp() {
        mailObserver = Mockito.mock(CargoObserver.class);
        smsObserver = Mockito.mock(CargoObserver.class);
        logObserver = Mockito.mock(CargoObserver.class);
        cargoStatusPublisher = new CargoStatusPublisher(List.of());
        cargoStatusPublisher.register(mailObserver);
        cargoStatusPublisher.register(smsObserver);
        cargoStatusPublisher.register(logObserver);
    }

    @Test
    void shouldNotifyAllRegisteredObservers() {
        Cargo cargo = Mockito.mock(Cargo.class);

        cargoStatusPublisher.notifyObservers(cargo);

        verify(mailObserver, times(1)).update(cargo);
        verify(smsObserver, times(1)).update(cargo);
        verify(logObserver, times(1)).update(cargo);
    }

    @Test
    void shouldNotNotifyUnregisteredObserver() {
        Cargo cargo = Mockito.mock(Cargo.class);
        cargoStatusPublisher.unregister(smsObserver);

        cargoStatusPublisher.notifyObservers(cargo);

        verify(mailObserver, times(1)).update(cargo);
        verify(logObserver, times(1)).update(cargo);
        verify(smsObserver, times(0)).update(cargo);
    }
}
