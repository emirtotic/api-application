package com.covidtracker.service.apiServices;

import com.covidtracker.dto.covid.CovidApiResponse;

import java.util.List;

public interface CovidApiService {

    List<CovidApiResponse> getAllCovidData(String countryCode);
}
