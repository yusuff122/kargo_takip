package com.yusuf.kargotakip.business.patterns.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yusuf.kargotakip.entities.concretes.Cargo;

@Component
public class SmsNotificationObserver implements CargoObserver {

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationObserver.class);

    @Override
    public void update(Cargo cargo) {
        logger.info("Kargo durum değişikliği SMS bildirimi gönderildi: trackingNumber={}, status={}",
                cargo.getTrackingNumber(), cargo.getStatus());
    }
}
