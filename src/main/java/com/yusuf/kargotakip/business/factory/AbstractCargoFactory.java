package com.yusuf.kargotakip.business.factory;

import java.util.UUID;

abstract class AbstractCargoFactory {

    protected String generateTrackingNumber() {
        return "KRG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
