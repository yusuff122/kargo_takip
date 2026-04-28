package com.yusuf.kargotakip.business.abstracts;

import com.yusuf.kargotakip.business.requests.CreateCargoRequest;
import com.yusuf.kargotakip.business.responses.GetCargoByTrackingNumberResponse;

public interface CargoService {

    GetCargoByTrackingNumberResponse add(CreateCargoRequest createCargoRequest);

    GetCargoByTrackingNumberResponse getByTrackingNumber(String trackingNumber);
}
