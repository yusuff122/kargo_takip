package com.yusuf.kargotakip.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.yusuf.kargotakip.business.factory.CargoFactoryResolver;
import com.yusuf.kargotakip.business.factory.ExpressCargoFactory;
import com.yusuf.kargotakip.business.factory.SpecialCargoFactory;
import com.yusuf.kargotakip.business.factory.StandardCargoFactory;
import com.yusuf.kargotakip.business.patterns.observer.CargoStatusPublisher;
import com.yusuf.kargotakip.business.patterns.strategy.ShippingCostStrategy;
import com.yusuf.kargotakip.business.patterns.strategy.ShippingCostStrategyResolver;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.requests.UpdateCargoStatusRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.responses.ShippingCostResponse;
import com.yusuf.kargotakip.business.rules.CargoBusinessRules;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.dataAccess.abstracts.CustomerRepository;
import com.yusuf.kargotakip.dataAccess.abstracts.StoreEmployeeRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.Customer;
import com.yusuf.kargotakip.entities.concretes.ExpressCargo;
import com.yusuf.kargotakip.entities.concretes.StandardCargo;
import com.yusuf.kargotakip.entities.concretes.StoreEmployee;

class CargoManagerTest {

    private CargoRepository cargoRepository;
    private CustomerRepository customerRepository;
    private StoreEmployeeRepository storeEmployeeRepository;
    private CargoStatusPublisher cargoStatusPublisher;
    private ShippingCostStrategyResolver shippingCostStrategyResolver;
    private CargoManager cargoManager;

    @BeforeEach
    void setUp() {
        cargoRepository = Mockito.mock(CargoRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        storeEmployeeRepository = Mockito.mock(StoreEmployeeRepository.class);
        cargoStatusPublisher = Mockito.mock(CargoStatusPublisher.class);
        shippingCostStrategyResolver = Mockito.mock(ShippingCostStrategyResolver.class);

        CargoBusinessRules cargoBusinessRules = new CargoBusinessRules(cargoRepository);
        CargoFactoryResolver cargoFactoryResolver = new CargoFactoryResolver(
                List.of(
                        new StandardCargoFactory(),
                        new ExpressCargoFactory(),
                        new SpecialCargoFactory()));

        cargoManager = new CargoManager(
                cargoRepository,
                customerRepository,
                storeEmployeeRepository,
                cargoFactoryResolver,
                cargoBusinessRules,
                cargoStatusPublisher,
                shippingCostStrategyResolver);
    }

    @Test
    void shouldCreateStandardCargoWithOrderAndTrackingNumber() {
        CreateCargoRequest request = new CreateCargoRequest(
                "TechMarket",
                "Mert",
                "Kaya",
                "mert@techmarket.com",
                "Ayşe",
                "Yılmaz",
                "ayse@example.com",
                256,
                CargoType.STANDARD);

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Ayşe")
                .lastName("Yılmaz")
                .email("ayse@example.com")
                .build();

        StoreEmployee storeEmployee = StoreEmployee.builder()
                .id(1L)
                .firstName("Mert")
                .lastName("Kaya")
                .email("mert@techmarket.com")
                .storeName("TechMarket")
                .build();
        ShippingCostStrategy standardStrategy = Mockito.mock(ShippingCostStrategy.class);

        when(customerRepository.findByEmail("ayse@example.com")).thenReturn(Optional.of(customer));
        when(storeEmployeeRepository.findByEmail("mert@techmarket.com")).thenReturn(Optional.of(storeEmployee));
        when(shippingCostStrategyResolver.resolve(CargoType.STANDARD)).thenReturn(standardStrategy);
        when(standardStrategy.calculate(any(Cargo.class))).thenReturn(new BigDecimal("120"));
        when(cargoRepository.save(any(Cargo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GetCargoByTrackingNumberResponse response = cargoManager.add(request);

        assertNotNull(response.getTrackingNumber());
        assertNotNull(response.getOrderNumber());
        assertEquals("STANDARD", response.getCargoType());
        assertEquals(new BigDecimal("120"), response.getShippingCost());
        assertEquals("Ayşe Yılmaz", response.getCustomerFullName());
        assertEquals(256, response.getDistanceKm());
    }

    @Test
    void shouldCreateExpressCargoWithPriorityDelivery() {
        CreateCargoRequest request = new CreateCargoRequest(
                "Hızlı Sepet",
                "Ece",
                "Bulut",
                "ece@hizlisepet.com",
                "Mehmet",
                "Kaya",
                "mehmet@example.com",
                340,
                CargoType.EXPRESS);

        when(customerRepository.findByEmail("mehmet@example.com")).thenReturn(Optional.of(
                Customer.builder().firstName("Mehmet").lastName("Kaya").email("mehmet@example.com").build()));
        when(storeEmployeeRepository.findByEmail("ece@hizlisepet.com")).thenReturn(Optional.of(
                StoreEmployee.builder().firstName("Ece").lastName("Bulut").email("ece@hizlisepet.com").storeName("Hızlı Sepet").build()));
        ShippingCostStrategy expressStrategy = Mockito.mock(ShippingCostStrategy.class);
        when(shippingCostStrategyResolver.resolve(CargoType.EXPRESS)).thenReturn(expressStrategy);
        when(expressStrategy.calculate(any(Cargo.class))).thenReturn(new BigDecimal("210"));
        when(cargoRepository.save(any(Cargo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GetCargoByTrackingNumberResponse response = cargoManager.add(request);

        assertEquals("EXPRESS", response.getCargoType());
        assertEquals(true, response.isPriorityDelivery());
        assertEquals("Hızlı Sepet", response.getStoreName());
        assertEquals(new BigDecimal("210"), response.getShippingCost());
    }

    @Test
    void shouldReturnCargoWhenOrderNumberExists() {
        Cargo cargo = ExpressCargo.builder()
                .id(1L)
                .orderNumber("SIP-TEST123")
                .trackingNumber("KRG-TEST123")
                .status(CargoStatus.HAZIRLANIYOR)
                .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                .storeEmployee(StoreEmployee.builder().firstName("Mert").lastName("Kaya").email("mert@techmarket.com").storeName("TechMarket").build())
                .notificationEmail("ayse@example.com")
                .distanceKm(220)
                .cargoType(CargoType.EXPRESS)
                .shippingCost(new BigDecimal("89.90"))
                .priorityDelivery(true)
                .specialHandling(false)
                .description("Hızlı teslimat - daha pahalı ve öncelikli taşıma")
                .build();
        when(cargoRepository.findByOrderNumber("SIP-TEST123")).thenReturn(Optional.of(cargo));

        GetCargoByTrackingNumberResponse response = cargoManager.getByOrderNumber("SIP-TEST123");

        assertEquals("SIP-TEST123", response.getOrderNumber());
        assertEquals("EXPRESS", response.getCargoType());
    }

    @Test
    void shouldThrowExceptionWhenOrderNumberDoesNotExist() {
        when(cargoRepository.findByOrderNumber("SIP-UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> cargoManager.getByOrderNumber("SIP-UNKNOWN"));
    }

    @Test
    void shouldUpdateCargoStatusAndNotifyObservers() {
        Cargo cargo = ExpressCargo.builder()
                .id(1L)
                .orderNumber("SIP-TEST123")
                .trackingNumber("KRG-TEST123")
                .status(CargoStatus.HAZIRLANIYOR)
                .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                .storeEmployee(StoreEmployee.builder().firstName("Mert").lastName("Kaya").email("mert@techmarket.com").storeName("TechMarket").build())
                .notificationEmail("ayse@example.com")
                .distanceKm(220)
                .cargoType(CargoType.EXPRESS)
                .shippingCost(new BigDecimal("89.90"))
                .priorityDelivery(true)
                .specialHandling(false)
                .description("Hızlı teslimat - daha pahalı ve öncelikli taşıma")
                .build();

        when(cargoRepository.findByTrackingNumber("KRG-TEST123")).thenReturn(Optional.of(cargo));
        when(cargoRepository.save(any(Cargo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GetCargoByTrackingNumberResponse response = cargoManager.updateCargoStatus(
                "KRG-TEST123",
                new UpdateCargoStatusRequest(CargoStatus.DAGITIMDA));

        assertEquals("DAGITIMDA", response.getStatus());
        verify(cargoRepository).save(cargo);
        verify(cargoStatusPublisher).notifyObservers(cargo);
    }

    @Test
    void shouldCalculateShippingCostWithResolvedStrategy() {
        Cargo cargo = ExpressCargo.builder()
                .id(1L)
                .orderNumber("SIP-TEST123")
                .trackingNumber("KRG-TEST123")
                .status(CargoStatus.HAZIRLANIYOR)
                .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                .storeEmployee(StoreEmployee.builder().firstName("Mert").lastName("Kaya").email("mert@techmarket.com").storeName("TechMarket").build())
                .notificationEmail("ayse@example.com")
                .cargoType(CargoType.EXPRESS)
                .shippingCost(new BigDecimal("89.90"))
                .priorityDelivery(true)
                .specialHandling(false)
                .description("Hızlı teslimat - daha pahalı ve öncelikli taşıma")
                .build();
        ShippingCostStrategy strategy = Mockito.mock(ShippingCostStrategy.class);

        when(cargoRepository.findByTrackingNumber("KRG-TEST123")).thenReturn(Optional.of(cargo));
        when(shippingCostStrategyResolver.resolve(CargoType.EXPRESS)).thenReturn(strategy);
        when(strategy.calculate(cargo)).thenReturn(new BigDecimal("150"));

        ShippingCostResponse response = cargoManager.calculateShippingCost("KRG-TEST123");

        assertEquals(new BigDecimal("150"), response.getCost());
        assertEquals("KRG-TEST123", response.getTrackingNumber());
        verify(strategy).calculate(cargo);
    }
}
