package com.yusuf.kargotakip.business.requests;

import com.yusuf.kargotakip.entities.concretes.CargoType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCargoRequest {

    @NotBlank(message = "Mağaza adı boş bırakılamaz.")
    private String storeName;

    @NotBlank(message = "Mağaza çalışanı adı boş bırakılamaz.")
    private String storeEmployeeFirstName;

    @NotBlank(message = "Mağaza çalışanı soyadı boş bırakılamaz.")
    private String storeEmployeeLastName;

    @Email(message = "Mağaza çalışanı e-posta adresi geçerli olmalıdır.")
    @NotBlank(message = "Mağaza çalışanı e-posta adresi boş bırakılamaz.")
    private String storeEmployeeEmail;

    @NotBlank(message = "Müşteri adı boş bırakılamaz.")
    private String customerFirstName;

    @NotBlank(message = "Müşteri soyadı boş bırakılamaz.")
    private String customerLastName;

    @Email(message = "Müşteri e-posta adresi geçerli olmalıdır.")
    @NotBlank(message = "Müşteri e-posta adresi boş bırakılamaz.")
    private String customerEmail;

    @Positive(message = "Mesafe bilgisi sıfırdan büyük olmalıdır.")
    private int distanceKm;

    @NotNull(message = "Kargo tipi boş bırakılamaz.")
    private CargoType cargoType;
}
