package com.yusuf.kargotakip.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yusuf.kargotakip.business.abstracts.CargoViewService;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.dataAccess.abstracts.CargoRepository;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.UserRole;

import lombok.RequiredArgsConstructor;

@Service("cargoViewManager")
@RequiredArgsConstructor
public class CargoViewManager implements CargoViewService {

    private final CargoRepository cargoRepository;

    @Override
    public List<Cargo> getAuthorizedCargoes(UserRole role, String mail) {
        return switch (role) {
            case CUSTOMER -> cargoRepository.findAllByCustomer_Email(mail);
            case SELLER -> cargoRepository.findAllByStoreEmployee_Email(mail);
            default -> throw new BusinessException("Desteklenmeyen kullanıcı rolü.");
        };
    }
}
