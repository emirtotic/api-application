package com.covidtracker.service.dbServices.impl;

import com.covidtracker.exception.ApplicationException;
import com.covidtracker.service.dbServices.JasperService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JasperServiceImpl implements JasperService {

    /**
     * Method for exporting the report
     *
     * @param reportPath Destination for created report file
     * @param params     Params which will be passed to the report
     * @param collection Collection with data which will be passed to report
     * @return InputStream
     */
    @Override
    public InputStream exportReportToPDF(String reportPath, Map<String, Object> params, Collection<?> collection) {

        try {
            byte[] bytes = JasperExportManager.exportReportToPdf(preparePrint(getReportDesign(reportPath), params, collection));
            return new ByteArrayInputStream(bytes);
        } catch (JRException | IOException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * Method for exporting the report
     *
     * @param reportPath Destination for created report file
     * @param params     Params which will be passed to the report
     * @param collection Collection with data which will be passed to report
     * @return InputStream
     */
    @Override
    public InputStream exportReportExcel(String reportPath, Map<String, Object> params, Collection<?> collection) {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            InputStream inputStream = getReportDesign(reportPath);
            JasperReport jasperReport = compileReport(inputStream);

            params.put("IS_PDF", false);
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(collection));

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenColumns(false);
            configuration.setRemoveEmptySpaceBetweenRows(false);
            configuration.setMaxRowsPerSheet(999999999);
            configuration.setIgnoreCellBorder(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            return new ByteArrayInputStream(baos.toByteArray());

        } catch (JRException | IOException e) {
            throw new ApplicationException("Error occurred while exporting excel report!");
        }
    }

    private InputStream getReportDesign(String reportPath) throws IOException {
        return new FileInputStream(new ClassPathResource(reportPath).getFile());
    }

    private JasperPrint preparePrint(InputStream reportXml, Map<String, Object> params, Collection<?> collection) throws JRException {
        JasperReport jasperReport = compileReport(reportXml);
        return collection != null ?
                JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(collection)) :
                JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }

    private JasperReport compileReport(InputStream inputStream) throws JRException {
        return JasperCompileManager.compileReport(inputStream);
    }
}