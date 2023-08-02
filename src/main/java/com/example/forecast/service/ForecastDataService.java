package com.example.forecast.service;


import java.util.List;
import com.example.forecast.entity.ForecastData;

public interface ForecastDataService {
    List<ForecastData> findByProductIdAndSiteId(String productId, String siteId);

    public List<ForecastData> getAllForecastData();
    public ForecastData getForecastDataById(String id);
    public ForecastData addOrUpdateForecastData(ForecastData forecastData);
    public void deleteForecastData(String id);
}

