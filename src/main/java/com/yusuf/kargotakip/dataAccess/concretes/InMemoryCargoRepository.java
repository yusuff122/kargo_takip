package com.yusuf.kargotakip.dataAccess.concretes;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;

@Repository
public class InMemoryCargoRepository implements CargoRepository {

    private final Map<String, Cargo> cargoTable = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Cargo save(Cargo cargo) {
        if (cargo.getId() == null) {
            cargo.setId(idGenerator.getAndIncrement());
        }

        cargoTable.put(cargo.getTrackingNumber(), cargo);
        return cargo;
    }

    @Override
    public Optional<Cargo> findByTrackingNumber(String trackingNumber) {
        return Optional.ofNullable(cargoTable.get(trackingNumber));
    }
}
