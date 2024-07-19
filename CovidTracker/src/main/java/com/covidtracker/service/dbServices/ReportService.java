package com.covidtracker.service.dbServices;

import java.io.InputStream;

public interface ReportService {

    /**
     * Create PDF report
     *
     * @return InputStream for creating pdf report
     */
    InputStream createPdfReport(String countryCode);

    /**
     * Create Excel report
     *
     * @return InputStream for creating excel .xlsx report
     */
    InputStream createExcelReport(String countryCode);
}
