package com.covidtracker.service.impl;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.dbServices.impl.MailSenderServiceImpl;
import com.covidtracker.service.dbServices.CovidDbService;
import com.covidtracker.service.dbServices.impl.CovidDbServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

class CovidDbServiceImplTest {

    private CovidDbRepository covidDbRepository;
    private CovidMapper covidMapper;
    private CovidDbService covidDbService;
    private MailSenderServiceImpl mailSenderService;

    @BeforeEach
    void setUp() {
        covidDbRepository = mock(CovidDbRepository.class);
        covidMapper = mock(CovidMapper.class);
        covidDbService = new CovidDbServiceImpl(covidDbRepository, covidMapper, mailSenderService );

    }

    @Test
    @DisplayName("Find All Records from DB Test")
    void getAllCovidDataTest() {
        CovidRecord covidRecord = createCovidRecord();
        List<CovidRecord> entityList = Collections.singletonList(covidRecord);
        when(covidDbRepository.findAll()).thenReturn(entityList);
        when(covidMapper.mapToDto(entityList)).thenReturn(Collections.singletonList(new CovidRecordDto()));

        covidDbService.findAllRecords();
        verify(covidDbRepository, times(1)).findAll();
        verify(covidMapper, times(1)).mapToDto(entityList);

    }

    @Test
    @DisplayName("Find All Records For Country Test")
    void findAllRecordsForCountryTest() {
        CovidRecord covidRecord = createCovidRecord();

        when(covidDbRepository.findAllRecordsForCountry(anyString())).thenReturn(covidRecord);
        when(covidMapper.mapToDto(any(CovidRecord.class))).thenReturn(new CovidRecordDto());

        covidDbService.findAllRecordsForCountry(anyString());

        verify(covidDbRepository, times(1)).findAllRecordsForCountry(anyString());
        verify(covidMapper, times(1)).mapToDto(any(CovidRecord.class));

    }

    @Test
    @DisplayName("Save Record in DB Test")
    void saveCovidDataTest() {
        CovidRecord covidRecord = createCovidRecord();

        when(covidDbRepository.findAllRecordsForCountry(anyString())).thenReturn(covidRecord);
        when(covidMapper.mapToDto(any(CovidRecord.class))).thenReturn(new CovidRecordDto());

        covidDbService.findAllRecordsForCountry(anyString());

        verify(covidDbRepository, times(1)).findAllRecordsForCountry(anyString());
        verify(covidMapper, times(1)).mapToDto(any(CovidRecord.class));

    }

    @Test
    @DisplayName("Delete Record from DB Test")
    void deleteCovidDataTest() {
        when(covidDbRepository.findAllRecordsForCountry(anyString())).thenReturn(new CovidRecord());
        doNothing().when(covidDbRepository).delete(any());
        covidDbService.deleteCovidDataByCountryCode(anyString());
        verify(covidDbRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Find All Balkan Records from DB Test")
    void findAllRecordsFromBalkanTest() {
        CovidRecord covidRecord = createCovidRecord();
        List<CovidRecord> entityList = Collections.singletonList(covidRecord);
        when(covidDbRepository.findAllRecordsFromBalkan()).thenReturn(entityList);
        when(covidMapper.mapToDto(entityList)).thenReturn(Collections.singletonList(new CovidRecordDto()));

        covidDbService.findAllRecordsFromBalkan();
        verify(covidDbRepository, times(1)).findAllRecordsFromBalkan();
        verify(covidMapper, times(1)).mapToDto(entityList);

    }

    @Test
    @DisplayName("Find Record For Country By NameTest")
    void findAllRecordsForCountryByNameTest() {
        CovidRecord covidRecord = createCovidRecord();

        when(covidDbRepository.findAllRecordsForCountryByName(anyString())).thenReturn(covidRecord);
        when(covidMapper.mapToDto(any(CovidRecord.class))).thenReturn(new CovidRecordDto());

        covidDbService.findAllRecordsForCountryByName(anyString());

        verify(covidDbRepository, times(1)).findAllRecordsForCountryByName(anyString());
        verify(covidMapper, times(1)).mapToDto(any(CovidRecord.class));

    }

    private CovidRecord createCovidRecord() {
        CovidRecord covidRecord = new CovidRecord();
        covidRecord.setId(1l);
        covidRecord.setCode("IT");
        covidRecord.setCountry("Italy");
        covidRecord.setConfirmed(100L);
        covidRecord.setCritical(5L);
        covidRecord.setRecovered(100L);
        covidRecord.setDeaths(0L);
        covidRecord.setLastChange(new Date());
        covidRecord.setLastUpdate(new Date());

        return covidRecord;
    }

}