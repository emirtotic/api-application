package com.covidtracker.dto.embassy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmbassyApiResponse {

    private String embassy;
    private String location;

    @JsonProperty("data")
    private List<Data> embassyList;

}
