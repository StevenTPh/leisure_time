package org.example.leisure_time.service;

import org.example.leisure_time.dto.RegionsResponseDTO;
import org.example.leisure_time.dto.WaterTemperatureResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class YrClient {

    private final RestTemplate restTemplate;

    public YrClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RegionsResponseDTO getRegions() {
        String url = "https://www.yr.no/api/v0/regions/NO";
        return restTemplate.getForObject(url, RegionsResponseDTO.class);
    }

    public List<WaterTemperatureResponseDTO> getWaterTemperatures(String regionId) {
        String url = "https://www.yr.no/api/v0/regions/{region-id}/watertemperatures";
        WaterTemperatureResponseDTO[] response = restTemplate.getForObject(url, WaterTemperatureResponseDTO[].class, regionId);
        return Arrays.asList(response);
    }
}