package com.yusuf.kargotakip.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.yusuf.kargotakip.business.abstracts.CargoViewService;
import com.yusuf.kargotakip.business.decorators.ExpressCargoCardDecorator;
import com.yusuf.kargotakip.business.decorators.SpecialCargoCardDecorator;
import com.yusuf.kargotakip.business.decorators.StandardCargoCardDecorator;
import com.yusuf.kargotakip.business.facade.CargoDashboardFacade;
import com.yusuf.kargotakip.business.responses.CargoPanelResponse;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.CargoStatus;
import com.yusuf.kargotakip.entities.concretes.CargoType;
import com.yusuf.kargotakip.entities.concretes.Customer;
import com.yusuf.kargotakip.entities.concretes.ExpressCargo;
import com.yusuf.kargotakip.entities.concretes.SpecialCargo;
import com.yusuf.kargotakip.entities.concretes.StandardCargo;
import com.yusuf.kargotakip.entities.concretes.StoreEmployee;
import com.yusuf.kargotakip.entities.concretes.UserRole;

class CargoDashboardPatternTest {

    private CargoRepository cargoRepository;
    private CargoViewProxy cargoViewProxy;
    private CargoDashboardFacade cargoDashboardFacade;

    @BeforeEach
    void setUp() {
        cargoRepository = Mockito.mock(CargoRepository.class);
        CargoViewService cargoViewManager = new CargoViewManager(cargoRepository);
        cargoViewProxy = new CargoViewProxy(cargoViewManager);
        cargoDashboardFacade = new CargoDashboardFacade(
                cargoViewProxy,
                List.of(
                        new StandardCargoCardDecorator(),
                        new ExpressCargoCardDecorator(),
                        new SpecialCargoCardDecorator()));
    }

    @Test
    void proxyShouldFilterCustomerCargoesByEmail() {
        when(cargoRepository.findAllByCustomer_Email("ayse@example.com")).thenReturn(List.of(
                StandardCargo.builder()
                
                        .orderNumber("SIP-11111111")
                        
                        .trackingNumber("KRG-11111111")
                        .status(CargoStatus.HAZIRLANIYOR)
                        .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                        
                        .storeEmployee(StoreEmployee.builder().firstName("Mert").lastName("Kaya").email("mert@techmarket.com").storeName("TechMarket").build())
                        
                        .notificationEmail("ayse@example.com")
                        .distanceKm(90)
                        .cargoType(CargoType.STANDARD)
                        .shippingCost(new BigDecimal("49.90"))
                        .priorityDelivery(false)
                        .specialHandling(false)
                        .description("Normal teslimat - en ucuz ve varsayılan kargo tipi")
                        .build()));

        List<Cargo> result = cargoViewProxy.getAuthorizedCargoes(UserRole.CUSTOMER, "ayse@example.com");

        assertEquals(1, result.size());
        assertEquals("ayse@example.com", result.get(0).getCustomer().getEmail());
    }

    @Test
    void facadeShouldBuildDashboardSummaryAndDecoratedCards() {
        when(cargoRepository.findAllByCustomer_Email("ayse@example.com")).thenReturn(List.of(
                ExpressCargo.builder()
                        .orderNumber("SIP-22222222")
                        .trackingNumber("KRG-22222222")
                        .status(CargoStatus.DAGITIMDA)
                        .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                        .storeEmployee(StoreEmployee.builder().firstName("Ece").lastName("Bulut").email("ece@hizlisepet.com").storeName("Hızlı Sepet").build())
                        .notificationEmail("ayse@example.com")
                        .distanceKm(240)
                        .cargoType(CargoType.EXPRESS)
                        .shippingCost(new BigDecimal("89.90"))
                        .priorityDelivery(true)
                        .specialHandling(false)
                        .description("Hızlı teslimat - daha pahalı ve öncelikli taşıma")
                        .build(),
                SpecialCargo.builder()
                        .orderNumber("SIP-33333333")
                        .trackingNumber("KRG-33333333")
                        .status(CargoStatus.TESLIM_EDILDI)
                        .customer(Customer.builder().firstName("Ayşe").lastName("Yılmaz").email("ayse@example.com").build())
                        .storeEmployee(StoreEmployee.builder().firstName("Selin").lastName("Aras").email("selin@mucevher.com").storeName("Mücevher Dünyası").build())
                        .notificationEmail("ayse@example.com")
                        .distanceKm(320)
                        .cargoType(CargoType.SPECIAL)
                        .shippingCost(new BigDecimal("129.90"))
                        .priorityDelivery(false)
                        .specialHandling(true)
                        .description("Kırılabilir veya değerli ürünler için ek korumalı özel kargo")
                        .build()));

        CargoPanelResponse panel = cargoDashboardFacade.buildPanel(UserRole.CUSTOMER, "ayse@example.com");

        assertEquals(2, panel.getSummary().getTotalCargoCount());
        assertEquals(1, panel.getSummary().getDeliveredCargoCount());
        assertEquals(1, panel.getSummary().getInTransitCargoCount());
        assertEquals("Hızlı Teslimat", panel.getCargoes().get(0).getBadgeLabel());
        assertEquals("Özel Koruma", panel.getCargoes().get(1).getBadgeLabel());
        assertEquals("Ayşe Yılmaz", panel.getCargoes().get(0).getCustomerFullName());
    }

    @Test
    void proxyShouldRejectBlankUsername() {
        assertThrows(BusinessException.class,
                () -> cargoViewProxy.getAuthorizedCargoes(UserRole.SELLER, " "));
    }
}
