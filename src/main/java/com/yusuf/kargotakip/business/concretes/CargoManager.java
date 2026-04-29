package com.yusuf.kargotakip.business.concretes;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.business.abstracts.CargoService;
import com.yusuf.kargotakip.business.factory.CargoFactory;
import com.yusuf.kargotakip.business.factory.CargoFactoryResolver;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoManager implements CargoService {

    private final CargoRepository cargoRepository;
    private final CargoFactoryResolver cargoFactoryResolver;
    private final CargoBusinessRules cargoBusinessRules;

    @Override
    public GetCargoByTrackingNumberResponse add(CreateCargoRequest createCargoRequest) {
        cargoBusinessRules.checkIfCargoNamesAreValid(
                createCargoRequest.getSenderName(),
                createCargoRequest.getReceiverName());

        CargoFactory cargoFactory = cargoFactoryResolver.resolve(createCargoRequest.getCargoType());
        Cargo cargo = cargoFactory.create(createCargoRequest);
        Cargo savedCargo = cargoRepository.save(cargo);
        return mapToResponse(savedCargo);
    }

    @Override
    public GetCargoByTrackingNumberResponse getByTrackingNumber(String trackingNumber) {
        cargoBusinessRules.checkIfCargoExists(trackingNumber);
        Cargo cargo = cargoRepository.findByTrackingNumber(trackingNumber).orElseThrow();
        return mapToResponse(cargo);
    }

    private GetCargoByTrackingNumberResponse mapToResponse(Cargo cargo) {
        return GetCargoByTrackingNumberResponse.builder()
                .trackingNumber(cargo.getTrackingNumber())
                .status(cargo.getStatus().name())
                .senderName(cargo.getSenderName())
                .receiverName(cargo.getReceiverName())
                .cargoType(cargo.getCargoType().name())
                .shippingCost(cargo.getShippingCost())
                .priorityDelivery(cargo.isPriorityDelivery())
                .specialHandling(cargo.isSpecialHandling())
                .description(cargo.getDescription())
                .build();
    }
}
