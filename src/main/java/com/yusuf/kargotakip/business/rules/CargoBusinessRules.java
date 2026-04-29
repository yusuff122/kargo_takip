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

    public void checkIfCargoNamesAreValid(String senderName, String receiverName) {
        if (senderName == null || senderName.isBlank()) {
            throw new BusinessException("Gönderici adı boş bırakılamaz.");
        }

        if (receiverName == null || receiverName.isBlank()) {
            throw new BusinessException("Alıcı adı boş bırakılamaz.");
        }
    }
}
