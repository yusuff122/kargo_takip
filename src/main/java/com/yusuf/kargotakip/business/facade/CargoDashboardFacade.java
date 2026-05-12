package com.yusuf.kargotakip.business.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.business.abstracts.CargoViewService;
import com.yusuf.kargotakip.business.decorators.CargoCardDecorator;
import com.yusuf.kargotakip.business.responses.CargoCardResponse;
import com.yusuf.kargotakip.business.responses.CargoPanelResponse;
import com.yusuf.kargotakip.business.responses.CargoPanelSummaryResponse;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.UserRole;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CargoDashboardFacade {

    private final CargoViewService cargoViewService;
    private final List<CargoCardDecorator> cargoCardDecorators;

    public CargoPanelResponse buildPanel(UserRole role, String username) {
        List<Cargo> cargoes = cargoViewService.getAuthorizedCargoes(role, username);
        List<CargoCardResponse> cargoCards = cargoes.stream()
                .map(this::toCargoCard)
                .map(this::decorateCard)
                .toList();

        CargoPanelSummaryResponse summary = CargoPanelSummaryResponse.builder()
                .totalCargoCount(cargoes.size())
                .deliveredCargoCount(cargoes.stream().filter(cargo -> cargo.getStatus() == CargoStatus.TESLIM_EDILDI).count())
                .inTransitCargoCount(cargoes.stream().filter(cargo -> cargo.getStatus() == CargoStatus.DAGITIMDA).count())
                .expressCargoCount(cargoes.stream().filter(cargo -> cargo.getCargoType() == CargoType.EXPRESS).count())
                .specialCargoCount(cargoes.stream().filter(cargo -> cargo.getCargoType() == CargoType.SPECIAL).count())
                .build();

        return CargoPanelResponse.builder()
                .username(username)
                .role(role.name())
                .summary(summary)
                .cargoes(cargoCards)
                .build();
    }

    private CargoCardResponse toCargoCard(Cargo cargo) {
        return CargoCardResponse.builder()
                .orderNumber(cargo.getOrderNumber())
                .trackingNumber(cargo.getTrackingNumber())
                .status(cargo.getStatus().name())
                .customerFullName(cargo.getCustomer().getFullName())
                .customerEmail(cargo.getCustomer().getEmail())
                .storeEmployeeFullName(cargo.getStoreEmployee().getFullName())
                .storeName(cargo.getStoreEmployee().getStoreName())
                .cargoType(cargo.getCargoType().name())
                .shippingCost(cargo.getShippingCost())
                .priorityDelivery(cargo.isPriorityDelivery())
                .specialHandling(cargo.isSpecialHandling())
                .description(cargo.getDescription())
                .statusDisplayText(getStatusDisplayText(cargo))
                .build();
    }

    private CargoCardResponse decorateCard(CargoCardResponse cargoCardResponse) {
        CargoType cargoType = CargoType.valueOf(cargoCardResponse.getCargoType());
        return cargoCardDecorators.stream()
                .filter(decorator -> decorator.supports(cargoType))
                .findFirst()
                .map(decorator -> decorator.decorate(cargoCardResponse))
                .orElse(cargoCardResponse);
    }

    private String getStatusDisplayText(Cargo cargo) {
        return switch (cargo.getStatus()) {
            case HAZIRLANIYOR -> "Gönderi hazırlanıyor";
            case KARGOYA_VERILDI -> "Kargo taşıma sürecine alındı";
            case DAGITIMDA -> "Kargo dağıtıma çıktı";
            case TESLIM_EDILDI -> "Kargo teslim edildi";
        };
    }
}
