package org.example.leisure_time.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegionsResponseDTO {
    String id;
    String name;
    String urlPath;
    private List<Region> regions;
}
