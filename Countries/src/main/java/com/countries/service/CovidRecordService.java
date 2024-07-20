package com.countries.service;

import com.countries.dto.CountryApiResponse;
import com.countries.entity.CovidRecord;

import java.util.Optional;

public interface CovidRecordService {

    void saveCovidRecord(CovidRecord covidRecord);
    void deleteCovidRecord(CovidRecord covidRecord);

    Optional<CovidRecord> findCovidRecordByThirdPartyIdAndCountry(Long thirdPartyId, String country);

}
