package com.yusuf.kargotakip.business.rules;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoBusinessRules {

    private final CargoRepository cargoRepository;

    public void checkIfCargoExists(String trackingNumber) {
        if (cargoRepository.findByTrackingNumber(trackingNumber).isEmpty()) {
            throw new BusinessException("Girilen takip numarasına ait kargo bulunamadı.");
        }
    }

    public void checkIfOrderExists(String orderNumber) {
        if (cargoRepository.findByOrderNumber(orderNumber).isEmpty()) {
            throw new BusinessException("Girilen sipariş numarasına ait kargo bulunamadı.");
        }
    }

    public void checkIfScenarioFieldsAreValid(String storeName, String customerEmail, String storeEmployeeEmail) {
        if (storeName == null || storeName.isBlank()) {
            throw new BusinessException("Mağaza adı boş bırakılamaz.");
        }

        if (customerEmail == null || customerEmail.isBlank()) {
            throw new BusinessException("Müşteri e-posta adresi boş bırakılamaz.");
        }

        if (storeEmployeeEmail == null || storeEmployeeEmail.isBlank()) {
            throw new BusinessException("Mağaza çalışanı e-posta adresi boş bırakılamaz.");
        }
    }
}
