package com.covidtracker.service.apiServices.impl;

import com.covidtracker.chatgpt.ChatGptRequest;
import com.covidtracker.chatgpt.ChatGptResponse;
import com.covidtracker.service.apiServices.ChatGptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptServiceImpl implements ChatGptService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.key}")
    private String key;

    @Value("${openai.api.url}")
    private String url;


    @Override
    public String getChatGptResponse(String prompt) {

        ChatGptRequest request = new ChatGptRequest(model, prompt);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + key);
        headers.set("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGptResponse> response = restTemplate.postForEntity(url, entity, ChatGptResponse.class);

        return handleResponse(response);
    }

    private String handleResponse(ResponseEntity<ChatGptResponse> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getChoices().get(0).toString();
        } else {
            return response.getStatusCode().toString();
        }
    }

}
