package com.yusuf.kargotakip.business.concretes;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.business.abstracts.CargoService;
import com.yusuf.kargotakip.business.factory.CargoFactory;
import com.yusuf.kargotakip.business.factory.CargoFactoryResolver;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.dataAccess.abstracts.CustomerRepository;
import com.yusuf.kargotakip.dataAccess.abstracts.StoreEmployeeRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.Customer;
import com.yusuf.kargotakip.entities.concretes.StoreEmployee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoManager implements CargoService {

    private final CargoRepository cargoRepository;
    private final CustomerRepository customerRepository;
    private final StoreEmployeeRepository storeEmployeeRepository;
    private final CargoFactoryResolver cargoFactoryResolver;
    private final CargoBusinessRules cargoBusinessRules;

    @Override
    public GetCargoByTrackingNumberResponse add(CreateCargoRequest createCargoRequest) {
        cargoBusinessRules.checkIfScenarioFieldsAreValid(
                createCargoRequest.getStoreName(),
                createCargoRequest.getCustomerEmail(),
                createCargoRequest.getStoreEmployeeEmail());

        Customer customer = getOrCreateCustomer(createCargoRequest);
        StoreEmployee storeEmployee = getOrCreateStoreEmployee(createCargoRequest);

        CargoFactory cargoFactory = cargoFactoryResolver.resolve(createCargoRequest.getCargoType());
        Cargo cargo = cargoFactory.create(createCargoRequest);
        cargo.setCustomer(customer);
        cargo.setStoreEmployee(storeEmployee);
        Cargo savedCargo = cargoRepository.save(cargo);

        return mapToResponse(savedCargo);
    }

    @Override
    public GetCargoByTrackingNumberResponse getByTrackingNumber(String trackingNumber) {
        cargoBusinessRules.checkIfCargoExists(trackingNumber);
        Cargo cargo = cargoRepository.findByTrackingNumber(trackingNumber).orElseThrow();

        return mapToResponse(cargo);
    }

    @Override
    public GetCargoByTrackingNumberResponse getByOrderNumber(String orderNumber) {
        cargoBusinessRules.checkIfOrderExists(orderNumber);
        Cargo cargo = cargoRepository.findByOrderNumber(orderNumber).orElseThrow();
        return mapToResponse(cargo);
    }

    private GetCargoByTrackingNumberResponse mapToResponse(Cargo cargo) {
        return GetCargoByTrackingNumberResponse.builder()
                .orderNumber(cargo.getOrderNumber())
                .trackingNumber(cargo.getTrackingNumber())
                .status(cargo.getStatus().name())
                .customerFullName(cargo.getCustomer().getFullName())
                .customerEmail(cargo.getCustomer().getEmail())
                .storeEmployeeFullName(cargo.getStoreEmployee().getFullName())
                .storeEmployeeEmail(cargo.getStoreEmployee().getEmail())
                .storeName(cargo.getStoreEmployee().getStoreName())
                .cargoType(cargo.getCargoType().name())
                .shippingCost(cargo.getShippingCost())
                .priorityDelivery(cargo.isPriorityDelivery())
                .specialHandling(cargo.isSpecialHandling())
                .description(cargo.getDescription())
                .notificationMessage("Sipariş numarası müşteri e-posta adresine iletilmeye hazır.")
                .build();
    }

    private Customer getOrCreateCustomer(CreateCargoRequest createCargoRequest) {
        return customerRepository.findByEmail(createCargoRequest.getCustomerEmail())
                .orElseGet(() -> customerRepository.save(Customer.builder()
                        .firstName(createCargoRequest.getCustomerFirstName())
                        .lastName(createCargoRequest.getCustomerLastName())
                        .email(createCargoRequest.getCustomerEmail())
                        .build()));
    }

    private StoreEmployee getOrCreateStoreEmployee(CreateCargoRequest createCargoRequest) {
        return storeEmployeeRepository.findByEmail(createCargoRequest.getStoreEmployeeEmail())
                .orElseGet(() -> storeEmployeeRepository.save(StoreEmployee.builder()
                        .firstName(createCargoRequest.getStoreEmployeeFirstName())
                        .lastName(createCargoRequest.getStoreEmployeeLastName())
                        .email(createCargoRequest.getStoreEmployeeEmail())
                        .storeName(createCargoRequest.getStoreName())
                        .build()));
    }
}
