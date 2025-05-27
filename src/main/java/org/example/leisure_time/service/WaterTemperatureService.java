package org.example.leisure_time.service;

import org.example.leisure_time.dto.BeachInfo;
import org.example.leisure_time.dto.RegionsResponseDTO;
import org.example.leisure_time.dto.WaterTemperatureResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WaterTemperatureService {

    @Autowired
    private YrClient yrClient;

    public List<BeachInfo> findWarmestWaterTemperaturePerRegion(List<String> regions) {
        if (regions == null || regions.isEmpty()) {
            throw new IllegalArgumentException("Regions list cannot be null or empty");
        }

        List<String> regionIds = getRegionsIds(regions);
        System.out.println(regionIds);
        List<WaterTemperatureResponseDTO> waterTemperatures = getWaterTemperatures(regionIds);

        List<BeachInfo> warmestWaterPerRegion = waterTemperatures.stream()
                .collect(Collectors.groupingBy(temp -> temp.getLocation().getRegion())) // Grupper etter fylke
                .entrySet()
                .stream()
                .map(entry -> entry.getValue().stream()
                        .max((temp1, temp2) -> Double.compare(temp1.getTemperature(), temp2.getTemperature()))
                        .orElse(null))
                .map(warmest -> BeachInfo.builder()
                        .regionName(warmest.getLocation().getRegion().getName())
                        .beachName(warmest.getLocation().getName())
                        .waterTemperature(warmest.getTemperature())
                        .build())
                .collect(Collectors.toList());

        return warmestWaterPerRegion;
    }

    private List<String> getRegionsIds(List<String> regions) {
        RegionsResponseDTO response = yrClient.getRegions();
        if (response == null || response.getRegions() == null) {
            throw new IllegalStateException("Failed to fetch regions");
        }

        // fjerner case sensitvititet
        List<String> regionNamesLower = regions.stream()
                .map(r -> r.toLowerCase())
                .toList();

        return response.getRegions().stream()
                .filter(region -> regionNamesLower.contains(region.getName().toLowerCase()))
                .map(region -> region.getId())
                .collect(Collectors.toList());
    }

    private List<WaterTemperatureResponseDTO> getWaterTemperatures(List<String> regionIds) {
        return regionIds.stream()
                .flatMap(regionId -> yrClient.getWaterTemperatures(regionId).stream())
                .collect(Collectors.toList());
    }
}
