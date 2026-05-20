package com.yusuf.kargotakip.business.patterns.observer;

import com.yusuf.kargotakip.entities.concretes.Cargo;

public interface CargoObserver {

    void update(Cargo cargo);
}
