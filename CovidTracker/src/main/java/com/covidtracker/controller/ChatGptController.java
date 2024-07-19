package com.covidtracker.controller;

import com.covidtracker.service.apiServices.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/bot/covid")
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    private static final Logger logger = LoggerFactory.getLogger(ChatGptController.class);

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam("prompt") String prompt) {
        try {

            String chatGptResponse = chatGptService.getChatGptResponse(prompt);

            return ResponseEntity.ok(chatGptResponse);
        } catch (HttpClientErrorException ex) {
            return handleException(ex);
        } catch (Exception ex) {
            logger.error("Unexpected error occurred", ex);
            throw new RuntimeException("Unexpected error: " + ex.getMessage());
        }
    }

    private ResponseEntity<String> handleException(HttpClientErrorException ex) {
        logger.error("Client error occurred", ex);
        return ResponseEntity.status(ex.getStatusCode()).body("Client error: " + ex.getMessage());
    }
}
