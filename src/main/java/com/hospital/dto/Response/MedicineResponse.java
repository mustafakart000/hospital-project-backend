package com.hospital.dto.Response;

import java.util.List;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private String manufacturer;
    private List<SpecialityDto> relatedSpecialties;
    private Integer dosage;
    private String insuranceCoverage;
    private Integer mg;
    private String coverage;
    private boolean isPrescriptionRequired;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecialityDto {
        private String code;
        private String name;
    }
} 