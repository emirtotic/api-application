package com.covidtracker.dto.covid;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CovidRecordDto {

    private String code;
    private String country;
    private Long confirmed;
    private Long recovered;
    private Long critical;
    private Long deaths;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy:HH:mm:ss")
    private LocalDateTime lastChange;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy:HH:mm:ss")
    private LocalDateTime lastUpdate;

}
