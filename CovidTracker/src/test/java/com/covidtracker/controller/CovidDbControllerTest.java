package com.covidtracker.controller;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.service.dbServices.CovidDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CovidDbControllerTest {

    private CovidDbController covidDbController;
    private CovidDbService covidDbService;

    @BeforeEach
    void setUp() {
        covidDbService = mock(CovidDbService.class);
        covidDbController = new CovidDbController(covidDbService);
    }

    @Test
    @DisplayName("Find All Records from DB Test")
    void findAllRecords() {
        when(covidDbService.findAllRecords()).thenReturn(Collections.singletonList(new CovidRecordDto()));
        List<CovidRecordDto> list = covidDbController.findAllRecords().getBody();
        assertEquals(1, list.size());
        verify(covidDbService, times(1)).findAllRecords();
    }

    @Test
    @DisplayName("Find Country Records from DB Test")
    void findAllRecordsForCountry() {
        when(covidDbService.findAllRecordsForCountry(anyString())).thenReturn(new CovidRecordDto());
        CovidRecordDto dto = covidDbController.findAllRecordsForCountry(anyString()).getBody();
        assertNotNull(dto);
        verify(covidDbService, times(1)).findAllRecordsForCountry(anyString());
    }

    @Test
    @DisplayName("Delete Country Records from DB Test")
    void deleteCovidData() {
        covidDbController.deleteCovidData(anyString());
        verify(covidDbService, times(1)).deleteCovidDataByCountryCode(anyString());
    }

    @Test
    @DisplayName("Find Records from DB for Balkan Test")
    void findAllRecordsFromBalkan() {
        when(covidDbService.findAllRecordsFromBalkan()).thenReturn(Collections.singletonList(new CovidRecordDto()));
        List<CovidRecordDto> list = covidDbController.findAllRecordsFromBalkan().getBody();
        assertEquals(1, list.size());
        verify(covidDbService, times(1)).findAllRecordsFromBalkan();
    }

    @Test
    @DisplayName("Find Pageable Records from DB Test")
    void findAllRecordsPaged() {
        Pageable pageable = PageRequest.of(1, 5);
        List<CovidRecordDto> dtos = Collections.singletonList(new CovidRecordDto());
        Page<CovidRecordDto> recordsPage = new PageImpl<>(dtos);
        covidDbController.findAllRecordsPaged(pageable.getPageNumber(), pageable.getPageSize());
        when(covidDbService.findAllRecords(pageable)).thenReturn(recordsPage);
        assertEquals(recordsPage.getContent().size(), dtos.size());
        verify(covidDbService, times(1)).findAllRecords(pageable);
    }

    @Test
    @DisplayName("Find Country Records from DB by name Test")
    void findAllRecordsForCountryByName() {
        when(covidDbService.findAllRecordsForCountryByName(anyString())).thenReturn(new CovidRecordDto());
        CovidRecordDto dto = covidDbController.findAllRecordsForCountryByName(anyString()).getBody();
        assertNotNull(dto);
        verify(covidDbService, times(1)).findAllRecordsForCountryByName(anyString());
    }
}