package org.example.leisure_time.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BeachInfo {
    private String regionName;
    private String beachName;
    private Double waterTemperature;
}
