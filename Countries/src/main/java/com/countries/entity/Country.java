package com.countries.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "country")
public class Country {

    @Id
    private String id;
    private String name;
    private String continent;
    private String officialLanguage;
    private String capital;
    private String government;
    private String borderLength;
    private Long population;
    private Long surfaceAreaSqMi;
    private Long surfaceAreaKm2;
    private Long populationDensitySqMi;
    private Long populationDensityKm2;
    private List<String> neighborsList;

}
