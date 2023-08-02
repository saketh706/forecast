package com.example.forecast.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.forecast.entity.ForecastFile;

public interface ForecastFileRepository extends MongoRepository<ForecastFile, String> {
    // add custom methods here
}

