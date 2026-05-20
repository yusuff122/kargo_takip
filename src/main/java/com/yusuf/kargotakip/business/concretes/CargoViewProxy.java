package com.yusuf.kargotakip.business.concretes;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yusuf.kargotakip.business.abstracts.CargoViewService;
import com.yusuf.kargotakip.core.exceptions.BusinessException;
import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.UserRole;

@Service
@Primary
public class CargoViewProxy implements CargoViewService {

    private static final Logger logger = LoggerFactory.getLogger(CargoViewProxy.class);

    private final CargoViewService target;

    public CargoViewProxy(@Qualifier("cargoViewManager") CargoViewService target) {
        this.target = target;
    }

    @Override
    public List<Cargo> getAuthorizedCargoes(UserRole role, String mail) {
    	
        if (role == null) {
        	
            throw new BusinessException("Kullanıcı rolü boş bırakılamaz.");
        }

        if (mail == null || mail.isBlank()) {
        	
            throw new BusinessException("Kullanıcı adı boş bırakılamaz.");
        }

        String normalizedUsername = normalizeUsername(mail);
        
        validateUsernameFormat(normalizedUsername);

        
        
        logger.info("Gönderi görüntüleme isteği alındı. Rol: {}, kullanıcı: {}", role, normalizedUsername);

        
        List<Cargo> authorizedCargoes = target.getAuthorizedCargoes(role, normalizedUsername);

        logger.info("Gönderi görüntüleme isteği tamamlandı. Rol: {}, kullanıcı: {}, kayıt sayısı: {}",
                role, normalizedUsername, authorizedCargoes.size());

        return authorizedCargoes;
    }

    private String normalizeUsername(String mail) {
        return mail.trim().toLowerCase(Locale.ROOT);
    }

    private void validateUsernameFormat(String mail) {
        if (mail.length() > 60) {
            throw new BusinessException("Kullanıcı bilgisi çok uzun.");
        }

        if (!mail.contains("@") || !mail.contains(".")) {
            throw new BusinessException("Geçerli bir e-posta adresi giriniz.");
        }
    }
}
