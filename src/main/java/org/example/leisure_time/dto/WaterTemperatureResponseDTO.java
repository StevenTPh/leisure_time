package org.example.leisure_time.dto;

import lombok.Data;

@Data
public class WaterTemperatureResponseDTO {

    private LocationDTO location;
    private int id;
    private double temperature;
    private String time;
    private int source;
    private String sourceDisplayName;
}
