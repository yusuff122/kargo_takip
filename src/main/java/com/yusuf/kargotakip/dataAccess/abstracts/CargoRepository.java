package com.yusuf.kargotakip.dataAccess.abstracts;

import java.util.Optional;

import com.yusuf.kargotakip.entities.concretes.Cargo;

public interface CargoRepository {

    Cargo save(Cargo cargo);

    Optional<Cargo> findByTrackingNumber(String trackingNumber);
}
