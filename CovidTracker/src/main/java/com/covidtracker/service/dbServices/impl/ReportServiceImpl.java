package com.covidtracker.service.dbServices.impl;

import com.covidtracker.dto.covid.CovidRecordDto;
import com.covidtracker.entity.CountryReport;
import com.covidtracker.service.dbServices.CovidDbService;
import com.covidtracker.service.dbServices.JasperService;
import com.covidtracker.service.dbServices.ReportService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CovidDbService covidDbService;
    private final JasperService jasperService;

    /**
     * Create PDF Covid report
     *
     * @param countryCode
     * @return InputStream for creating pdf report
     */
    @Override
    public InputStream createPdfReport(String countryCode) {

        log.info("Generating Covid19 PDF report...");

        Map<String, Object> params = new HashMap<>();
        CountryReport countryReport = prepareDataForCountryReport(countryCode);
        params.put("countryReport", countryReport);

        log.info("Generating PDF report for [{}] country...", countryCode);

        return jasperService.exportReportToPDF("/report/country-report.jrxml",
                params,
                Collections.singletonList(countryReport));
    }

    /**
     * Create Excel Covid report
     *
     * @param countryCode
     * @return InputStream for creating excel .xlsx report
     */
    @Override
    public InputStream createExcelReport(String countryCode) {

        log.info("Generating Covid19 Excel report...");

        Map<String, Object> params = new HashMap<>();
        CountryReport countryReport = prepareDataForCountryReport(countryCode);
        params.put("countryReport", countryReport);
        List<CountryReport> collection = new ArrayList<>();
        collection.add(countryReport);

        return jasperService.exportReportExcel("/report/country-report.jrxml",
                params,
                collection);
    }

    private CountryReport prepareDataForCountryReport(String code) {

        CountryReport countryReport = new CountryReport();
        CovidRecordDto allRecordsForCountry = covidDbService.findAllRecordsForCountry(code);
        log.info("Preparing data for the report...");

        if (allRecordsForCountry != null) {
            countryReport.setTitle("Covid19 report for " + allRecordsForCountry.getCountry());
            countryReport.setDescription("During the pandemic, we recorded the following results: ");
            countryReport.setCode(allRecordsForCountry.getCode());
            countryReport.setCountry(allRecordsForCountry.getCountry());
            countryReport.setConfirmed(allRecordsForCountry.getConfirmed());
            countryReport.setRecovered(allRecordsForCountry.getRecovered());
            countryReport.setCritical(allRecordsForCountry.getCritical());
            countryReport.setDeaths(allRecordsForCountry.getDeaths());
            countryReport.setLastChange(allRecordsForCountry.getLastChange());
            countryReport.setLastUpdate(allRecordsForCountry.getLastUpdate());

        }

        return countryReport;
    }
}