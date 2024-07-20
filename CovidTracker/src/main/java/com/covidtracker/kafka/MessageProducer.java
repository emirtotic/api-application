package com.covidtracker.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, CovidRecordForSending> kafkaTemplate;

    public void sendMessage(String topic, CovidRecordForSending covidRecordForSending) {
        kafkaTemplate.send(topic, covidRecordForSending);
    }

}
