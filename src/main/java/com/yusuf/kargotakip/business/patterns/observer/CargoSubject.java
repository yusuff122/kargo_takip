package com.yusuf.kargotakip.business.patterns.observer;

import com.yusuf.kargotakip.entities.concretes.Cargo;

public interface CargoSubject {

    void register(CargoObserver observer);

    void unregister(CargoObserver observer);

    void notifyObservers(Cargo cargo);
}
