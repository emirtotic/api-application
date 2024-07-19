package com.covidtracker.service.apiServices.impl;

import com.covidtracker.dto.country.CountryApiResponse;
import com.covidtracker.entity.Country;
import com.covidtracker.mapper.CountryMapper;
import com.covidtracker.repository.CountriesDbRepository;
import com.covidtracker.service.apiServices.CountryApiService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CountryApiServiceImpl implements CountryApiService {

    @Autowired
    private CountriesDbRepository countriesDbRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryMapper countryMapper;

    @Value("${countriesApiUrl}")
    private String countries;
    @Value("${x-rapidapi-key}")
    private String key;
    @Value("${x-rapidapi-host}")
    private String host;

    @Override
    public List<CountryApiResponse> getAllCountries() {

        List<CountryApiResponse> countryApiResponses = new ArrayList<>();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", key);
            headers.set("x-rapidapi-host", host);

            ResponseEntity<String> response = restTemplate.exchange(countries,
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Response: {}", response.getBody());

            log.info("Fetching codes data for countries...");

            ObjectMapper mapper = new ObjectMapper();
            countryApiResponses = mapper.readValue(response.getBody(), new TypeReference<List<CountryApiResponse>>() {});

            countryApiResponses.forEach(c -> {
                Optional<Country> dbCountry = Optional.ofNullable(countriesDbRepository.findByName(c.getName()));
                if (!dbCountry.isPresent()) {
                    countriesDbRepository.save(countryMapper.mapFromApiResponseToDbEntity(c));
                }
            });

            return countryApiResponses;

        } catch (JsonProcessingException e) {
            log.error("Error occurred while fetching countries data!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

}
