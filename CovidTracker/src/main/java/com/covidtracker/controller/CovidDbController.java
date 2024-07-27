package com.covidtracker.controller;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.service.dbServices.CovidDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/covid-results")
@RequiredArgsConstructor
public class CovidDbController {

    private final CovidDbService covidDbService;

    @GetMapping("/all")
    public ResponseEntity<List<CovidRecordDto>> findAllRecords() {
        return new ResponseEntity<>(covidDbService.findAllRecords(), HttpStatus.OK);
    }

    @GetMapping("/country/code/{countryCode}")
    public ResponseEntity<CovidRecordDto> findAllRecordsForCountry(@PathVariable(name = "countryCode") String countryCode) {
        return new ResponseEntity<>(covidDbService.findAllRecordsForCountry(countryCode), HttpStatus.OK);
    }

    @DeleteMapping("/country/code/{countryCode}")
    public ResponseEntity<String> deleteCovidData(@PathVariable(name = "countryCode") String countryCode) {
        return new ResponseEntity<>(covidDbService.deleteCovidDataByCountryCode(countryCode), HttpStatus.OK);
    }

    @GetMapping("/all/balkan")
    public ResponseEntity<List<CovidRecordDto>> findAllRecordsFromBalkan() {
        return new ResponseEntity<>(covidDbService.findAllRecordsFromBalkan(), HttpStatus.OK);
    }

    @GetMapping("/paged/all")
    public ResponseEntity<Page<CovidRecordDto>> findAllRecordsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CovidRecordDto> recordsPage = covidDbService.findAllRecords(pageable);
        return new ResponseEntity<>(recordsPage, HttpStatus.OK);
    }

    @GetMapping("/country/name/{countryName}")
    public ResponseEntity<CovidRecordDto> findAllRecordsForCountryByName(@PathVariable(name = "countryName") String countryName) {
        return new ResponseEntity<>(covidDbService.findAllRecordsForCountryByName(countryName), HttpStatus.OK);
    }

    @GetMapping("/country/code/{countryCode}/{email}")
    public ResponseEntity<CovidRecordDto> sendAnEmailWithReport(@PathVariable(name = "countryCode") String countryCode,
                                                                @PathVariable(name = "email") String email) {
        return new ResponseEntity<>(covidDbService.sendAnEmailWithReport(countryCode, email), HttpStatus.OK);
    }

}
