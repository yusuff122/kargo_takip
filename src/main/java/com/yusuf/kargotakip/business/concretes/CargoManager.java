package com.yusuf.kargotakip.business.concretes;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.business.abstracts.CargoService;
import com.yusuf.kargotakip.business.factory.CargoFactory;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;

@Service
public class CargoManager implements CargoService {

    private final CargoRepository cargoRepository;
    private final CargoFactory cargoFactory;
    private final CargoBusinessRules cargoBusinessRules;

    public CargoManager(CargoRepository cargoRepository, CargoFactory cargoFactory,
            CargoBusinessRules cargoBusinessRules) {
        this.cargoRepository = cargoRepository;
        this.cargoFactory = cargoFactory;
        this.cargoBusinessRules = cargoBusinessRules;
    }

    @Override
    public GetCargoByTrackingNumberResponse add(CreateCargoRequest createCargoRequest) {
        cargoBusinessRules.checkIfCargoNamesAreValid(
                createCargoRequest.getSenderName(),
                createCargoRequest.getReceiverName());

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
        GetCargoByTrackingNumberResponse response = new GetCargoByTrackingNumberResponse();
        response.setTrackingNumber(cargo.getTrackingNumber());
        response.setStatus(cargo.getStatus().name());
        response.setSenderName(cargo.getSenderName());
        response.setReceiverName(cargo.getReceiverName());
        return response;
    }
}
