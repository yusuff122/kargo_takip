package com.yusuf.kargotakip.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yusuf.kargotakip.entities.concretes.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByTrackingNumber(String trackingNumber);
}
