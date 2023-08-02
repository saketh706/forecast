package com.example.forecast.service;

import com.example.forecast.entity.DataValues;
import com.example.forecast.entity.ForecastData;
import com.example.forecast.entity.ForecastFile;
import com.example.forecast.repository.ForecastDataRepository;
import com.example.forecast.repository.ForecastFileRepository;
import com.example.forecast.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.forecast.utils.DateUtils.parseDate;

@Slf4j
@Service
public class CsvUploadService {

    @Autowired
    private ForecastDataRepository forecastDataRepo;

    @Autowired
    private ForecastFileRepository forecastFileRepo;

    @Async
    //@Transactional
    public void uploadCsv(MultipartFile file, Boolean isSticky, Integer priority) throws IOException, ParseException {

        // Create a ForecastFile object and save it to the database
        ForecastFile forecastFile = new ForecastFile();
        forecastFile.setFileName(file.getOriginalFilename());
        forecastFile.setFileLink("http://abcd.com/" + file.getOriginalFilename());
        forecastFile.setTotalRows(0);
        forecastFile.setTotalErrors(0);
        forecastFile.setFeedType("ONLINE");
        forecastFile.setStatus("S");
        forecastFile.setIsDeleted(false);
        if(null != isSticky){
            forecastFile.setIsSticky(true);
            forecastFile.setStickyStartDate(DateUtils.formatDate(new Date()));
            forecastFile.setStickyEndDate(DateUtils.addDate(new Date(), 28));
        } else {
            forecastFile.setIsSticky(false);
        }
        if(null != priority){
            forecastFile.setPriority(priority);
        }
        forecastFile.setCreatedAt(new Date());
        forecastFile.setCreatedBy("saketh");
        forecastFile.setLastUpdatedAt(new Date());
        forecastFile.setLastUpdatedBy("saketh");
        forecastFile = forecastFileRepo.save(forecastFile);

        // Open the CSV file using the given file name
       // ClassPathResource resource = new ClassPathResource(file);
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

        // Loop through the CSV records and create ForecastData objects
        for (CSVRecord csvRecord : csvParser) {
            log.info("Saving forecast record to DB");
            for (String header : csvParser.getHeaderNames()) {
                ForecastData forecastData = new ForecastData();
                forecastData.setProductId(csvRecord.get("PRODUCT_ID"));
                forecastData.setSiteId(csvRecord.get("SITE_ID"));
                forecastData.setForecastFileId(forecastFile.getId());
                if (!header.equals("PRODUCT_ID") && !header.equals("SITE_ID")) {
                    //dataValuesList.add(new DataValues(DateUtils.formatDate(header), Integer.parseInt(csvRecord.get(header))));
                    forecastData.setDate(DateUtils.parseDate(header));
                    forecastData.setQuantity(Integer.parseInt(csvRecord.get(header)));
                    forecastData.setStatus("N");
                    forecastData.setCreatedAt(new Date());
                    forecastData.setCreatedBy("saketh");
                    forecastData.setLastUpdatedAt(new Date());
                    forecastData.setLastUpdatedBy("saketh");
                    // Save the ForecastData object to the database
                    forecastDataRepo.save(forecastData);
                }
            }
            log.info("Completed saving forecast record to DB");
        }
        forecastFile.setTotalRows((int) csvParser.getRecordNumber());
        forecastFile.setLastUpdatedAt(new Date());
        forecastFile.setLastUpdatedBy("saketh");
        forecastFileRepo.save(forecastFile);
        log.info("Forecast parsed successfully.");
        // Close the CSV parser
        csvParser.close();
    }


}

