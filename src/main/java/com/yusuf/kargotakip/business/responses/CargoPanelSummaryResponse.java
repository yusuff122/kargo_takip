package com.yusuf.kargotakip.business.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoPanelSummaryResponse {

    private long totalCargoCount;
    private long deliveredCargoCount;
    private long inTransitCargoCount;
    private long expressCargoCount;
    private long specialCargoCount;
}
