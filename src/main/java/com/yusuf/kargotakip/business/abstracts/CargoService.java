package com.yusuf.kargotakip.business.abstracts;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.requests.UpdateCargoStatusRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;
import com.yusuf.kargotakip.business.responses.ShippingCostResponse;

public interface CargoService {

    GetCargoByTrackingNumberResponse add(CreateCargoRequest createCargoRequest);

    GetCargoByTrackingNumberResponse getByTrackingNumber(String trackingNumber);

    GetCargoByTrackingNumberResponse getByOrderNumber(String orderNumber);

    GetCargoByTrackingNumberResponse updateCargoStatus(String trackingNumber, UpdateCargoStatusRequest updateCargoStatusRequest);

    ShippingCostResponse calculateShippingCost(String trackingNumber);
}
