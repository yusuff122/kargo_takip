package com.yusuf.kargotakip.core.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yusuf.kargotakip.business.abstracts.CargoService;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;

@Configuration
public class SampleDataInitializer {

    @Bean
    CommandLineRunner seedCargoData(CargoRepository cargoRepository, CargoService cargoService) {
        return args -> {
            if (cargoRepository.count() > 0) {
                return;
            }

            GetCargoByTrackingNumberResponse standard = cargoService.add(
                    new CreateCargoRequest("TechMarket", "Mert", "Kaya", "mert@techmarket.com",
                            "Ayşe", "Yılmaz", "ayse@example.com", 85, CargoType.STANDARD));
            GetCargoByTrackingNumberResponse express = cargoService.add(
                    new CreateCargoRequest("TechMarket", "Mert", "Kaya", "mert@techmarket.com",
                            "Ayşe", "Yılmaz", "ayse@example.com", 240, CargoType.EXPRESS));
            GetCargoByTrackingNumberResponse special = cargoService.add(
                    new CreateCargoRequest("Mücevher Dünyası", "Selin", "Aras", "selin@mucevher.com",
                            "Ayşe", "Yılmaz", "ayse@example.com", 320, CargoType.SPECIAL));
            GetCargoByTrackingNumberResponse books = cargoService.add(
                    new CreateCargoRequest("Kitap Noktası", "Deniz", "Yalçın", "deniz@kitap.com",
                            "Mehmet", "Kaya", "mehmet@example.com", 180, CargoType.STANDARD));
            GetCargoByTrackingNumberResponse fast = cargoService.add(
                    new CreateCargoRequest("Hızlı Sepet", "Ece", "Bulut", "ece@hizlisepet.com",
                            "Mehmet", "Kaya", "mehmet@example.com", 410, CargoType.EXPRESS));
            GetCargoByTrackingNumberResponse art = cargoService.add(
                    new CreateCargoRequest("Sanat Atölyesi", "Levent", "Aydın", "levent@sanat.com",
                            "Elif", "Demir", "elif@example.com", 260, CargoType.SPECIAL));

            assignSampleOrderNumber(cargoRepository, standard.getTrackingNumber(), "SIP-AYSE1001");
            assignSampleOrderNumber(cargoRepository, express.getTrackingNumber(), "SIP-AYSE1002");
            assignSampleOrderNumber(cargoRepository, special.getTrackingNumber(), "SIP-AYSE1003");
            assignSampleOrderNumber(cargoRepository, books.getTrackingNumber(), "SIP-MEHM1001");
            assignSampleOrderNumber(cargoRepository, fast.getTrackingNumber(), "SIP-MEHM1002");
            assignSampleOrderNumber(cargoRepository, art.getTrackingNumber(), "SIP-ELIF1001");

            updateStatus(cargoRepository, express.getTrackingNumber(), CargoStatus.DAGITIMDA);
            updateStatus(cargoRepository, special.getTrackingNumber(), CargoStatus.TESLIM_EDILDI);
            updateStatus(cargoRepository, books.getTrackingNumber(), CargoStatus.KARGOYA_VERILDI);
            updateStatus(cargoRepository, fast.getTrackingNumber(), CargoStatus.DAGITIMDA);
            updateStatus(cargoRepository, art.getTrackingNumber(), CargoStatus.TESLIM_EDILDI);
        };
    }

    private void assignSampleOrderNumber(CargoRepository cargoRepository, String trackingNumber, String orderNumber) {
        cargoRepository.findByTrackingNumber(trackingNumber).ifPresent(cargo -> {
            cargo.setOrderNumber(orderNumber);
            cargoRepository.save(cargo);
        });
    }

    private void updateStatus(CargoRepository cargoRepository, String trackingNumber, CargoStatus cargoStatus) {
        cargoRepository.findByTrackingNumber(trackingNumber).ifPresent(cargo -> {
            cargo.setStatus(cargoStatus);
            cargoRepository.save(cargo);
        });
    }
}
