package com.countries.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CovidRecordDto {

    private Long id;
    private String country;
    private String code;
    private Long confirmed;
    private Long recovered;
    private Long critical;
    private Long deaths;
    private Date lastChange;
    private Date lastUpdate;

}
