package com.countries.service.impl;

import com.countries.entity.CovidRecord;
import com.countries.repository.CovidRecordRepository;
import com.countries.service.CovidRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CovidRecordServiceImpl implements CovidRecordService {

    @Autowired
    private CovidRecordRepository covidRecordRepository;

    @Override
    public void saveCovidRecord(CovidRecord covidRecord) {
        covidRecordRepository.save(covidRecord);
    }

    @Override
    public void deleteCovidRecord(CovidRecord covidRecord) {
        covidRecordRepository.delete(covidRecord);
    }

    @Override
    public Optional<CovidRecord> findCovidRecordByThirdPartyIdAndCountry(Long thirdPartyId, String country) {
        return covidRecordRepository.findCovidRecordByThirdPartyIdAndCountry(thirdPartyId, country);
    }
}
