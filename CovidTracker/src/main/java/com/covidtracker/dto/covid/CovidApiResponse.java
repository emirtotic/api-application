package com.covidtracker.dto.covid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CovidApiResponse {

    private String country;
    private String code;
    private Long confirmed;
    private Long recovered;
    private Long critical;
    private Long deaths;
    private String latitude;
    private String longitude;
    private Date lastChange;
    private Date lastUpdate;

}
