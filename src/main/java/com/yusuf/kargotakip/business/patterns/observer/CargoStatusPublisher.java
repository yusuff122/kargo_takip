package com.yusuf.kargotakip.business.patterns.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.entities.concretes.Cargo;

@Component
public class CargoStatusPublisher implements CargoSubject {

    private final List<CargoObserver> observers = new ArrayList<>();

    public CargoStatusPublisher(List<CargoObserver> observers) {
        observers.forEach(this::register);
    }

    @Override
    public void register(CargoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(CargoObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Cargo cargo) {
        observers.forEach(observer -> observer.update(cargo));
    }
}
