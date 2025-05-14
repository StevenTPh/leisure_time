package org.example.leisure_time.controller;

import org.example.leisure_time.dto.BeachInfo;
import org.example.leisure_time.service.WaterTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/watertemperatures")
public class WaterTemperatureController {

    @Autowired
    private WaterTemperatureService waterTemperatureService;

    @PostMapping("/warmest")
    public BeachInfo findWarmestWaterTemperatureInNorway(@RequestBody List<String> regions) {
        return waterTemperatureService.findWarmestWaterTemperatureInNorway(regions);
    }
}
