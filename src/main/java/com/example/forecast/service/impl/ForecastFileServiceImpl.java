package com.example.forecast.service.impl;

import com.example.forecast.entity.ForecastFile;
import com.example.forecast.service.ForecastFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastFileServiceImpl implements ForecastFileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ForecastFile> getAllForecastFiles() {
        return null;
    }

    @Override
    public ForecastFile getForecastFileById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, ForecastFile.class);
    }

    @Override
    public ForecastFile addOrUpdateForecastFile(ForecastFile forecastFile) {
        return null;
    }

    @Override
    public void deleteForecastFile(String id) {

    }
}
