package com.example.forecast.service;

import java.util.List;
import com.example.forecast.entity.ForecastFile;

public interface ForecastFileService {
    public List<ForecastFile> getAllForecastFiles();
    public ForecastFile getForecastFileById(String id);
    public ForecastFile addOrUpdateForecastFile(ForecastFile forecastFile);
    public void deleteForecastFile(String id);
}

