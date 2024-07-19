package com.covidtracker.mapper;

import com.covidtracker.dto.covid.CovidApiResponse;
import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CovidMapperTest {

    private CovidMapper covidMapper;
    private CovidRecord covidRecord;
    private CovidRecordDto covidRecordDto;
    private CovidApiResponse covidApiResponse;

    @BeforeEach
    void setUp() {
        covidMapper = Mappers.getMapper(CovidMapper.class);
        covidRecord = createCovidRecord();
        covidRecordDto = createCovidRecordDto();
        covidApiResponse = createCovidApiResponse();
    }

    @Test
    @DisplayName("Map from API to Entity Test")
    void mapFromApiResponseToDbEntity() {

        CovidRecord entity = covidMapper.mapFromApiResponseToDbEntity(covidApiResponse);

        assertEquals(entity.getCode(), covidApiResponse.getCode());
        assertEquals(entity.getCountry(), covidApiResponse.getCountry());
        assertEquals(entity.getConfirmed(), covidApiResponse.getConfirmed());
        assertEquals(entity.getRecovered(), covidApiResponse.getRecovered());
        assertEquals(entity.getCritical(), covidApiResponse.getCritical());
        assertEquals(entity.getDeaths(), covidApiResponse.getDeaths());
        assertEquals(entity.getLastChange(), covidApiResponse.getLastChange());
        assertEquals(entity.getLastUpdate(), covidApiResponse.getLastUpdate());

    }

    @Test
    @DisplayName("Map from Entity to DTO Test")
    void mapToDto() {

        CovidRecordDto dto = covidMapper.mapToDto(covidRecord);

        assertEquals(dto.getCode(), covidRecord.getCode());
        assertEquals(dto.getCountry(), covidRecord.getCountry());
        assertEquals(dto.getConfirmed(), covidRecord.getConfirmed());
        assertEquals(dto.getRecovered(), covidRecord.getRecovered());
        assertEquals(dto.getCritical(), covidRecord.getCritical());
        assertEquals(dto.getDeaths(), covidRecord.getDeaths());
        assertEquals(dto.getLastChange(), LocalDateTime.ofInstant(
                covidRecord.getLastChange().toInstant(), ZoneId.of("UTC")));
        assertEquals(dto.getLastUpdate(), LocalDateTime.ofInstant(
                covidRecord.getLastUpdate().toInstant(), ZoneId.of("UTC")));

    }

    @Test
    @DisplayName("Map from Entity list to DTO list Test")
    void testMapToDto() {

        List<CovidRecord> entityList = Collections.singletonList(covidRecord);
        List<CovidRecordDto> dtoList = covidMapper.mapToDto(entityList);
        assertEquals(dtoList.size(), entityList.size());

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

    private CovidRecordDto createCovidRecordDto() {
        CovidRecordDto dto = new CovidRecordDto();
        dto.setCode("IT");
        dto.setCountry("Italy");
        dto.setConfirmed(100L);
        dto.setCritical(5L);
        dto.setRecovered(100L);
        dto.setDeaths(0L);
        dto.setLastChange(LocalDateTime.now());
        dto.setLastUpdate(LocalDateTime.now());

        return dto;
    }

    private CovidApiResponse createCovidApiResponse() {
        CovidApiResponse dto = new CovidApiResponse();
        dto.setCode("IT");
        dto.setCountry("Italy");
        dto.setConfirmed(100L);
        dto.setCritical(5L);
        dto.setRecovered(100L);
        dto.setDeaths(0L);
        dto.setLastChange(new Date());
        dto.setLastUpdate(new Date());

        return dto;
    }

}