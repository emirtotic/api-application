package com.covidtracker.service.dbServices.impl;

import com.covidtracker.dto.embassy.EmbassyApiResponse;
import com.covidtracker.service.dbServices.EmbassyApiService;
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

@Service
@Slf4j
public class EmbassyApiServiceImpl implements EmbassyApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapidEmbassyApiUrl}")
    private String rapidEmbassyApiUrl;
    @Value("${x-rapidapi-ua}")
    private String name;
    @Value("${x-rapidapi-key}")
    private String key;
    @Value("${x-embassy-rapidapi-host}")
    private String host;

    @Override
    public EmbassyApiResponse findYourEmbassies(String source, String destination) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-ua", name);
            headers.set("x-rapidapi-key", key);
            headers.set("x-rapidapi-host", host);

            StringBuilder sb = new StringBuilder();
            String url = sb.append(rapidEmbassyApiUrl)
                    .append("?source=")
                    .append(source.toLowerCase())
                    .append("&destination=")
                    .append(destination.toLowerCase())
                    .toString();

            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Response: {}", response.getBody());

            log.info("Finding " + source + " embassies in " + destination + "...");

            ObjectMapper mapper = new ObjectMapper();
            EmbassyApiResponse embassyApiResponse
                    = mapper.readValue(response.getBody(), new TypeReference<EmbassyApiResponse>() {});

            embassyApiResponse.setEmbassy("Embassy of " + source);
            embassyApiResponse.setLocation(destination);

            return embassyApiResponse;

        } catch (JsonProcessingException e) {
            log.error("Error occurred while fetching Embassy data.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }
}
