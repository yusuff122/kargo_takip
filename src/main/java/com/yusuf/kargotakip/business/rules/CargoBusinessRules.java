package com.yusuf.kargotakip.business.rules;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;

@Service
public class CargoBusinessRules {

    private final CargoRepository cargoRepository;

    public CargoBusinessRules(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void checkIfCargoExists(String trackingNumber) {
        if (cargoRepository.findByTrackingNumber(trackingNumber).isEmpty()) {
            throw new BusinessException("Girilen takip numarasina ait kargo bulunamadi.");
        }
    }

    public void checkIfCargoNamesAreValid(String senderName, String receiverName) {
        if (senderName == null || senderName.isBlank()) {
            throw new BusinessException("Gonderici adi bos birakilamaz.");
        }

        if (receiverName == null || receiverName.isBlank()) {
            throw new BusinessException("Alici adi bos birakilamaz.");
        }
    }
}
