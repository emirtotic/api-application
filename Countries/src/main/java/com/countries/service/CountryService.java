package com.countries.service;

import com.countries.dto.CountryApiResponse;

public interface CountryService {

    CountryApiResponse getCountryInfo(String countryName);

}
