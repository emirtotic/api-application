package com.countries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryApiResponse {

    private String countryName;
    private String continent;
    private String officialLanguages;
    private String capital;
    private String government;
    private String borderLength;
    private String population;
    private String surfaceAreaSqMi;
    private String surfaceAreaKm2;
    private String populationDensitySqMi;
    private String populationDensityKm2;
    private List<String> neighborsList;


}