package com.countries.service.impl;

import com.countries.dto.CountryApiResponse;
import com.countries.entity.Country;
import com.countries.repository.CountryRepository;
import com.countries.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapidCountryApiUrl}")
    private String countries;
    @Value("${x-rapidapi-key}")
    private String key;
    @Value("${x-country-rapidapi-host}")
    private String host;

    @Override
    public CountryApiResponse getCountryInfo(String countryName) {

        CountryApiResponse countryApiResponse = new CountryApiResponse();
        Optional<Country> country = countryRepository.findCountryByName(countryName.toLowerCase());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", key);
            headers.set("x-rapidapi-host", host);

            StringBuilder sb = new StringBuilder();
            String url = sb.append(countries).append(countryName).toString();

            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Response: {}", response.getBody());

            log.info("Getting country data...");

            ObjectMapper mapper = new ObjectMapper();
            countryApiResponse = mapper.readValue(response.getBody(), new TypeReference<CountryApiResponse>() {});

            if (!country.isPresent()) {
                Country newCountry = assembleCountry(countryApiResponse);
                countryRepository.save(newCountry);
            }

            return countryApiResponse;

        } catch (JsonProcessingException e) {
            log.error("Error occurred while fetching countries data!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }

    private Country assembleCountry(CountryApiResponse countryApiResponse) {

        Country country = new Country();
        country.setName(countryApiResponse.getCountryName().toLowerCase());
        country.setContinent(countryApiResponse.getContinent());
        country.setOfficialLanguage(countryApiResponse.getOfficialLanguages());
        country.setGovernment(countryApiResponse.getGovernment());
        country.setBorderLength(countryApiResponse.getBorderLength());
        country.setPopulation(Long.valueOf(countryApiResponse.getPopulation().replaceAll("\\s", "")));
        country.setSurfaceAreaSqMi(Long.valueOf(countryApiResponse.getSurfaceAreaSqMi().replaceAll("\\s", "")));
        country.setSurfaceAreaKm2(Long.valueOf(countryApiResponse.getSurfaceAreaSqMi().replaceAll("\\s", "")));
        country.setPopulationDensitySqMi(Long.valueOf(countryApiResponse.getPopulationDensitySqMi().replaceAll("\\s", "")));
        country.setPopulationDensityKm2(Long.valueOf(countryApiResponse.getPopulationDensityKm2().replaceAll("\\s", "")));
        country.setNeighborsList(countryApiResponse.getNeighborsList());

        return country;
    }

}
