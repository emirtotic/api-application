package com.covidtracker.service.apiServices;

import com.covidtracker.dto.country.CountryApiResponse;

import java.util.List;

public interface CountryApiService {

    List<CountryApiResponse> getAllCountries();
}
