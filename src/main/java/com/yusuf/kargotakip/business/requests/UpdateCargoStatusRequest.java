package com.yusuf.kargotakip.business.requests;

import com.yusuf.kargotakip.entities.concretes.CargoStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCargoStatusRequest {

    @NotNull(message = "Durum bilgisi boş bırakılamaz.")
    private CargoStatus status;
}
