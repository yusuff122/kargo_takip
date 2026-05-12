package com.yusuf.kargotakip.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yusuf.kargotakip.entities.concretes.StoreEmployee;

public interface StoreEmployeeRepository extends JpaRepository<StoreEmployee, Long> {

    Optional<StoreEmployee> findByEmail(String email);
}
