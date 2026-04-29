package com.yusuf.kargotakip.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.yusuf.kargotakip.entities.concretes.CargoType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCargoRequest {

    @NotBlank(message = "Gönderici adı boş bırakılamaz.")
    private String senderName;

    @NotBlank(message = "Alıcı adı boş bırakılamaz.")
    private String receiverName;

    @NotNull(message = "Kargo tipi boş bırakılamaz.")
    private CargoType cargoType;
}
