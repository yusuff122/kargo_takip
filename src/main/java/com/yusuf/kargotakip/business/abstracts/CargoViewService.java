package com.yusuf.kargotakip.business.abstracts;

import java.util.List;

import com.yusuf.kargotakip.entities.concretes.Cargo;
import com.yusuf.kargotakip.entities.concretes.UserRole;

public interface CargoViewService {

    List<Cargo> getAuthorizedCargoes(UserRole role, String username);
}
