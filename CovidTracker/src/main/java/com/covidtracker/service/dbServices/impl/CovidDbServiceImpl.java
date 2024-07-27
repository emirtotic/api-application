package com.covidtracker.service.dbServices.impl;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.exception.CovidRecordNotFoundException;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.MailSenderService;
import com.covidtracker.service.dbServices.CovidDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CovidDbServiceImpl implements CovidDbService {

    private final CovidDbRepository covidDbRepository;

    private final CovidMapper covidMapper;

    private final MailSenderService mailSenderService;

    @Override
    public List<CovidRecordDto> findAllRecords() {
        return covidMapper.mapToDto(covidDbRepository.findAll());
    }

    @Override
    public CovidRecordDto findAllRecordsForCountry(String countryCode) {
        log.info("Fetching covid data for [{}]...", countryCode);
        mailSenderService.sendNewMail("emirtotic@gmail.com", "TEST", "Proba!");
        return covidMapper.mapToDto(covidDbRepository.findAllRecordsForCountry(countryCode));
    }

    @Override
    public void saveCovidData(CovidRecord covidRecord) {
        log.info("Saving covid data for {}.", covidRecord.getCountry());
        covidDbRepository.save(covidRecord);
    }

    @Override
    public String deleteCovidDataByCountryCode(String countryCode) {

        CovidRecord record = Optional.ofNullable(covidDbRepository.findAllRecordsForCountry(countryCode))
                .orElseThrow(() -> new CovidRecordNotFoundException(countryCode));

        if (record != null) {
            log.info("Deleting covid data...");
            covidDbRepository.delete(record);

            return "Data successfully deleted.";
        }
        return "Could not retrieve data for desired country code.";
    }

    @Override
    public List<CovidRecordDto> findAllRecordsFromBalkan() {
        log.info("Fetching covid data for Balkan countries.");
        return covidMapper.mapToDto(covidDbRepository.findAllRecordsFromBalkan());
    }

    @Override
    public Page<CovidRecordDto> findAllRecords(Pageable pageable) {
        log.info("Fetching covid data for all countries - Pageable");
        return covidDbRepository.findAll(pageable).map(covidMapper::mapToDto);
    }

    @Override
    public CovidRecordDto findAllRecordsForCountryByName(String countryName) {
        log.info("Fetching covid data for {}...", countryName);
        return covidMapper.mapToDto(covidDbRepository.findAllRecordsForCountryByName(countryName));
    }

    @Override
    public CovidRecordDto sendAnEmailWithReport(String countryCode, String email) {
        CovidRecordDto covidRecordDto = covidMapper.mapToDto(covidDbRepository.findAllRecordsForCountry(countryCode));
        log.info("Fetching covid data for [{}] and sending it via email to {}...", covidRecordDto.getCountry(), email);
        mailSenderService.sendNewMail(email,
                "Covid Report for " + covidRecordDto.getCountry(),
                assembleEmailContent(covidRecordDto));
        return covidRecordDto;
    }

    private String assembleEmailContent(CovidRecordDto covidRecordDto) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Dear client, \n")
                .append("\nYou have requested covid data for ")
                .append(covidRecordDto.getCountry())
                .append(".\nPlease find results below: \n\n")
                .append(covidRecordDto)
                .append("\n\n")
                .append("Kind regards, \n")
                .append("Covid Tracker App").toString();
    }
}
