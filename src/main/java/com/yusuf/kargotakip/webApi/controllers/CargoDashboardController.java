package com.yusuf.kargotakip.webApi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yusuf.kargotakip.business.facade.CargoDashboardFacade;
import com.yusuf.kargotakip.business.responses.CargoPanelResponse;
import com.yusuf.kargotakip.entities.concretes.UserRole;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cargo-panel")
@RequiredArgsConstructor
public class CargoDashboardController {

    private final CargoDashboardFacade cargoDashboardFacade;

    @GetMapping
    public CargoPanelResponse getCargoPanel(@RequestParam UserRole role, @RequestParam String username) {
        return cargoDashboardFacade.buildPanel(role, username);
    }
}
