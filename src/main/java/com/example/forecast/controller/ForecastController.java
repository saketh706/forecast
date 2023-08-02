package com.example.forecast.controller;

import com.example.forecast.entity.ForecastData;
import com.example.forecast.entity.ForecastFile;
import com.example.forecast.repository.ForecastDataAggregationRepository;
import com.example.forecast.repository.ForecastFileRepository;
import com.example.forecast.service.CsvUploadService;
import com.example.forecast.service.ForecastDataService;
import com.example.forecast.service.ForecastFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    @Autowired
    private ForecastDataService forecastDataService;

    @Autowired
    private ForecastFileService forecastFileService;

    @Autowired
    private CsvUploadService csvUploadService;

    @Autowired
    private ForecastFileRepository forecastFileRepository;

    @Autowired
    private ForecastDataAggregationRepository forecastDataAggregationRepository;

    public ForecastController() {
    }

    @GetMapping("/{productId}/{siteId}")
    public ResponseEntity<List<ForecastData>> getForecastData(
            @PathVariable("productId") String productId,
            @PathVariable("siteId") String siteId) {

        List<ForecastData> forecastData = forecastDataService.findByProductIdAndSiteId(productId, siteId);
        if (forecastData == null || forecastData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forecastData);
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<ForecastFile> getForecastFile(@PathVariable("fileId") String fileId) {
        ForecastFile forecastFile = forecastFileService.getForecastFileById(fileId);
        if (forecastFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forecastFile);
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data", "application/octet-stream"})
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "sticky", required = false) Boolean isSticky,
                                           @RequestParam(value = "priority", required = false) Integer priority) throws IOException, ParseException {

        csvUploadService.uploadCsv(file,isSticky, priority);
        return "File uploaded successfully";
    }

    @GetMapping("/process")
    public ResponseEntity<String> process() {
        forecastDataAggregationRepository.procees();
        return ResponseEntity.ok("Process Started successfully.");
    }

    @PutMapping("/update/{fileId}/priority/{priority}")
    public ResponseEntity<ForecastFile> getForecastFile(@PathVariable("fileId") String fileId, @PathVariable("priority") int priority) {
        ForecastFile forecastFile = forecastFileService.getForecastFileById(fileId);
        if (forecastFile == null) {
            return ResponseEntity.notFound().build();
        }
        forecastFile.setPriority(priority);
        forecastFileRepository.save(forecastFile);
        return ResponseEntity.ok(forecastFile);
    }
}
