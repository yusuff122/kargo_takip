package com.yusuf.kargotakip.business.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoPanelResponse {

    private String username;
    private String role;
    private CargoPanelSummaryResponse summary;
    private List<CargoCardResponse> cargoes;
}
