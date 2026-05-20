package com.yusuf.kargotakip.webApi.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yusuf.kargotakip.business.abstracts.CargoService;
import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.requests.UpdateCargoStatusRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.responses.ShippingCostResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cargoes")
@Validated
@RequiredArgsConstructor
public class CargoesController {

    private final CargoService cargoService;

    @PostMapping
    public GetCargoByTrackingNumberResponse add(@Valid @RequestBody CreateCargoRequest createCargoRequest) {
        return cargoService.add(createCargoRequest);
    }

    @GetMapping("/{trackingNumber}")
    public GetCargoByTrackingNumberResponse getByTrackingNumber(@PathVariable String trackingNumber) {
        return cargoService.getByTrackingNumber(trackingNumber);
    }

    @GetMapping("/order/{orderNumber}")
    public GetCargoByTrackingNumberResponse getByOrderNumber(@PathVariable String orderNumber) {
        return cargoService.getByOrderNumber(orderNumber);
    }

    @PutMapping("/{trackingNumber}/status")
    public GetCargoByTrackingNumberResponse updateCargoStatus(
            @PathVariable String trackingNumber,
            @Valid @RequestBody UpdateCargoStatusRequest updateCargoStatusRequest) {
        return cargoService.updateCargoStatus(trackingNumber, updateCargoStatusRequest);
    }

    @GetMapping("/{trackingNumber}/shipping-cost")
    public ShippingCostResponse calculateShippingCost(@PathVariable String trackingNumber) {
        return cargoService.calculateShippingCost(trackingNumber);
    }
}
