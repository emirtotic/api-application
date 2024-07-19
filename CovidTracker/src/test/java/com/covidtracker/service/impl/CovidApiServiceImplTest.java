package com.covidtracker.service.impl;

import com.covidtracker.dto.covid.CovidApiResponse;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.dbServices.CovidDbService;
import com.covidtracker.service.apiServices.impl.CovidApiServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CovidApiServiceImplTest {

    @InjectMocks
    private CovidApiServiceImpl covidApiServiceImpl;

    @Mock
    private CovidDbRepository covidDbRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CovidDbService covidDbService;

    @Mock
    private CovidMapper covidMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Value("${rapidApiUrl}")
    private String rapidApiUrl = "https://api.example.com/";

    @Value("${x-rapidapi-key}")
    private String key = "test-key";

    @Value("${x-rapidapi-host}")
    private String host = "test-host";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all data for Country from Third Party Test")
    public void getAllCovidData() throws JsonProcessingException {
        // Arrange
        String countryCode = "US";
        String jsonResponse = "[{\"code\":\"US\",\"country\":\"United States\",\"confirmed\":100,\"recovered\":50,\"critical\":10,\"deaths\":5,\"lastChange\":\"2023-07-01\",\"lastUpdate\":\"2023-07-01\"}]";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", key);
        headers.set("x-rapidapi-host", host);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        CovidApiResponse apiResponse = new CovidApiResponse();
        apiResponse.setCode("US");
        apiResponse.setCountry("United States");
        apiResponse.setConfirmed(100l);
        apiResponse.setRecovered(50l);
        apiResponse.setCritical(10l);
        apiResponse.setDeaths(5l);
        apiResponse.setLastChange(new Date());
        apiResponse.setLastUpdate(new Date());

        List<CovidApiResponse> apiResponseList = Collections.singletonList(apiResponse);
        CovidRecord covidRecord = new CovidRecord();

        when(restTemplate.exchange(eq(rapidApiUrl + countryCode), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        when(objectMapper.readValue(eq(jsonResponse), any(TypeReference.class)))
                .thenReturn(apiResponseList);

        when(covidDbRepository.findAllRecordsForCountry(eq("US")))
                .thenReturn(covidRecord);

        when(covidMapper.mapFromApiResponseToDbEntity(eq(apiResponse)))
                .thenReturn(covidRecord);

        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.exchange(rapidApiUrl + countryCode,
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

        List<CovidApiResponse> covidApiResponse = mapper.readValue(response.getBody(), new TypeReference<List<CovidApiResponse>>() {});

        List<CovidApiResponse> result = covidApiResponse;

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("US", result.get(0).getCode());
    }
}
