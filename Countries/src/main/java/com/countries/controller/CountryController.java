package com.countries.controller;

import com.countries.dto.CountryApiResponse;
import com.countries.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/data")
    public ResponseEntity<CountryApiResponse> getCountryInfo(@RequestParam String countryName) {
        CountryApiResponse countryInfo = countryService.getCountryInfo(countryName);
        return new ResponseEntity<>(countryInfo, HttpStatus.OK);
    }


}
