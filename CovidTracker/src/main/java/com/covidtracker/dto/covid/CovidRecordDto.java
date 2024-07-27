package com.covidtracker.dto.covid;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Country: ").append(country)
                .append("\nCountry code: ").append(code)
                .append("\nConfirmed: ").append(confirmed)
                .append("\nRecovered: ").append(recovered)
                .append("\nCritical: ").append(critical)
                .append("\nDeaths: ").append(deaths)
                .append("\nLast Change: ").append(lastChange)
                .append("\nLast Update: ").append(lastUpdate);

        return sb.toString();
    }
}
