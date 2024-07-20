package com.countries.kafka;

import com.countries.dto.CovidRecordDto;
import com.countries.entity.CovidRecord;
import com.countries.entity.RecordLogger;
import com.countries.enums.LoggerStatus;
import com.countries.enums.Sender;
import com.countries.service.CovidRecordService;
import com.countries.service.RecordLoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class MessageConsumer {

    @Autowired
    private CovidRecordService covidRecordService;

    @Autowired
    private RecordLoggerService recordLoggerService;

    @KafkaListener(topics = "covid", groupId = "covid-group-id")
    public void listen(String message) {
        System.out.println("Covid Record received: " + message + "\n");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CovidRecordDto covidRecordDto = objectMapper.readValue(message, CovidRecordDto.class);

            CovidRecord covidRecord = assembleCovidRecord(covidRecordDto);

            Optional<CovidRecord> existingRecord
                    = covidRecordService.findCovidRecordByThirdPartyIdAndCountry(covidRecordDto.getId(),
                    covidRecordDto.getCountry());

            if (existingRecord.isPresent()) {
                log.info("Updating covid record for " + existingRecord.get().getCountry() + "\n");
                covidRecordService.deleteCovidRecord(existingRecord.get());
                covidRecordService.saveCovidRecord(covidRecord);

                recordLoggerService.saveCovidRecord(RecordLogger.builder()
                        .received(message)
                        .receivedFrom(Sender.COVID_TRACKER_APP.getName())
                        .status(LoggerStatus.OK.getStatus())
                        .updated(true)
                        .createdAt(new Date())
                        .build());
            } else {
                log.info("Creating covid record for " + covidRecord.getCountry() + "\n");
                covidRecordService.saveCovidRecord(covidRecord);
                recordLoggerService.saveCovidRecord(RecordLogger.builder()
                        .received(message)
                        .receivedFrom(Sender.COVID_TRACKER_APP.getName())
                        .status(LoggerStatus.OK.getStatus())
                        .updated(false)
                        .createdAt(new Date())
                        .build());
            }

        } catch (Exception e) {
            recordLoggerService.saveCovidRecord(RecordLogger.builder()
                    .received(message)
                    .receivedFrom(Sender.COVID_TRACKER_APP.getName())
                    .status(LoggerStatus.FAILED.getStatus())
                    .updated(false)
                    .createdAt(new Date())
                    .build());
            e.printStackTrace();
        }

    }

    private static CovidRecord assembleCovidRecord(CovidRecordDto covidRecordDto) {
        CovidRecord covidRecord = new CovidRecord();
        covidRecord.setThirdPartyId(covidRecordDto.getId());
        covidRecord.setCountry(covidRecordDto.getCountry());
        covidRecord.setCode(covidRecordDto.getCode());
        covidRecord.setConfirmed(covidRecordDto.getConfirmed());
        covidRecord.setRecovered(covidRecordDto.getRecovered());
        covidRecord.setCritical(covidRecordDto.getCritical());
        covidRecord.setDeaths(covidRecordDto.getDeaths());
        covidRecord.setLastChange(covidRecordDto.getLastChange());
        covidRecord.setLastUpdate(covidRecordDto.getLastUpdate());
        return covidRecord;
    }

}
