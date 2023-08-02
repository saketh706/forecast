package com.example.forecast.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.forecast.entity.ForecastData;

public interface ForecastDataRepository extends MongoRepository<ForecastData, String> {
    // add custom methods here
}

