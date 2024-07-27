package com.covidtracker.service.dbServices;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CovidDbService {

    List<CovidRecordDto> findAllRecords();
    CovidRecordDto findAllRecordsForCountry(String countryCode);
    void saveCovidData(CovidRecord covidRecord);
    String deleteCovidDataByCountryCode(String countryCode);
    List<CovidRecordDto> findAllRecordsFromBalkan();
    Page<CovidRecordDto> findAllRecords(Pageable pageable);
    CovidRecordDto findAllRecordsForCountryByName(String countryName);
    CovidRecordDto sendAnEmailWithReport(String countryCode, String email);
}
