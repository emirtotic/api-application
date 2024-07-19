package com.covidtracker.service.dbServices;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public interface JasperService {
    InputStream exportReportToPDF(String reportPath, Map<String, Object> params, Collection<?> collection);

    /**
     * Method for exporting the report
     * @param reportPath Destination for created report file
     * @param params Params which will be passed to the report
     * @param collection Collection with data which will be passed to report
     * @return InputStream
     */
    InputStream exportReportExcel(String reportPath, Map<String, Object> params, Collection<?> collection);
}
