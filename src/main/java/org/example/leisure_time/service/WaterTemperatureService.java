package org.example.leisure_time.service;

import org.example.leisure_time.dto.BeachInfo;
import org.example.leisure_time.dto.RegionsResponseDTO;
import org.example.leisure_time.dto.WaterTemperatureResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaterTemperatureService {

    @Autowired
    private YrClient yrClient;

    public BeachInfo findWarmestWaterTemperatureInNorway(List<String> regions) {
        if (regions == null || regions.isEmpty()) {
            throw new IllegalArgumentException("Regions list cannot be null or empty");
        }

        List<String> regionIds = getRegionsIds(regions);
        List<WaterTemperatureResponseDTO> waterTemperatures = getWaterTemperatures(regionIds);

        //find the warmest water temperature
        WaterTemperatureResponseDTO warmestWaterTemperature = waterTemperatures.stream()
                .max((temp1, temp2) -> Double.compare(temp1.getTemperature(), temp2.getTemperature()))
                .orElse(null);

        return BeachInfo.builder()
                .beachName(warmestWaterTemperature.getLocation().getName())
                .waterTemperature(warmestWaterTemperature.getTemperature())
                .build();
    }

    private List<String> getRegionsIds(List<String> regions) {
        RegionsResponseDTO response = yrClient.getRegions();
        if (response == null || response.getRegions() == null) {
            throw new IllegalStateException("Failed to fetch regions");
        }

        return response.getRegions().stream()
                .filter(region -> regions.contains(region.getName())) // Match by name
                .map(region -> region.getId())
                .collect(Collectors.toList());
    }

    private List<WaterTemperatureResponseDTO> getWaterTemperatures(List<String> regionIds) {
        return regionIds.stream()
                .flatMap(regionId -> yrClient.getWaterTemperatures(regionId).stream())
                .collect(Collectors.toList());
    }
}
