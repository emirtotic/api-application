package com.covidtracker.controller;

import com.covidtracker.service.dbServices.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/covid-report")
@Validated
public class CovidReportController {

    private final ReportService reportService;

    @GetMapping("/pdf/{countryCode}")
    public void createPdfReport(@PathVariable String countryCode, HttpServletResponse response) throws IOException {

        InputStream inputStream = reportService.createPdfReport(countryCode);

        if (inputStream != null) {
            String generatedFileName = "Covid19-report.pdf";
            response.setContentType(URLConnection.guessContentTypeFromName(generatedFileName));
            response.setHeader("Content-Disposition", "attachment; filename=" + generatedFileName);

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            response.setStatus(HttpStatus.CREATED.value());
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/excel/{countryCode}")
    public ResponseEntity<String> createExcelReport(@PathVariable String countryCode, HttpServletResponse response) throws IOException {

        InputStream inputStream = reportService.createExcelReport(countryCode);

        if (inputStream != null) {
            String generatedFileName = "Covid19-report.xlsx";
            response.setContentType(URLConnection.guessContentTypeFromName(generatedFileName));
            response.setHeader("Content-Disposition", "attachment; filename=" + generatedFileName);

            try {
                final ServletOutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}