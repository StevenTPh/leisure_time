package org.example.leisure_time.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private String id;
    private String name;
    private int elevation;
    private String timeZone;
    private String urlPath;
    private CountryDTO country;
    private Region region;
    private boolean isInOcean;
}
