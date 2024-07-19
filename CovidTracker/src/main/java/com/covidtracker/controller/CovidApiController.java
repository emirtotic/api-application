package com.covidtracker.controller;

import com.covidtracker.dto.country.CountryApiResponse;
import com.covidtracker.dto.covid.CovidApiResponse;
import com.covidtracker.dto.embassy.EmbassyApiResponse;
import com.covidtracker.service.apiServices.CountryApiService;
import com.covidtracker.service.dbServices.EmbassyApiService;
import com.covidtracker.service.apiServices.impl.CovidApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covid")
public class CovidApiController {

    @Autowired
    private CovidApiServiceImpl covidService;

    @Autowired
    private CountryApiService countryApiService;

    @Autowired
    private EmbassyApiService embassyApiService;

    @GetMapping("/get-all-data/{countryCode}")
    public ResponseEntity<List<CovidApiResponse>> getCovidDataForCountry(@PathVariable("countryCode") String countryCode) {

        return ResponseEntity.ok(covidService.getAllCovidData(countryCode));

    }

    @GetMapping("/get-all-countries")
    public ResponseEntity<List<CountryApiResponse>> getCountries() {

        return ResponseEntity.ok(countryApiService.getAllCountries());

    }

    @PostMapping("/find-your-embassies")
    public ResponseEntity<EmbassyApiResponse> findYourEmbassies(@RequestParam("source") String source,
                                                                      @RequestParam("destination") String destination) {

        return ResponseEntity.ok(embassyApiService.findYourEmbassies(source, destination));

    }



}
