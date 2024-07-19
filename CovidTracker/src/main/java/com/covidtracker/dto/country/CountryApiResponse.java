package com.covidtracker.dto.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryApiResponse {

    private String name;
    private String alpha2code;
    private String alpha3code;
    @JsonIgnore
    private String latitude;
    @JsonIgnore
    private String longitude;

}
