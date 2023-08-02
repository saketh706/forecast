package com.example.forecast.service.impl;

import com.example.forecast.entity.ForecastData;
import com.example.forecast.entity.ForecastFile;
import com.example.forecast.service.ForecastDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastDataServiceImpl implements ForecastDataService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ForecastData save(ForecastData forecastData) {
        return mongoTemplate.save(forecastData);
    }
@Override
    public List<ForecastData> findByProductIdAndSiteId(String productId, String siteId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product_id").is(productId)
                .and("site_id").is(siteId));
        return mongoTemplate.find(query, ForecastData.class);
    }

    public void delete(ForecastData forecastData) {
        mongoTemplate.remove(forecastData);
    }

    public ForecastFile save(ForecastFile forecastFile) {
        return mongoTemplate.save(forecastFile);
    }

    public ForecastFile findByForecastFileId(String forecastFileId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(forecastFileId));
        return mongoTemplate.findOne(query, ForecastFile.class);
    }

    @Override
    public List<ForecastData> getAllForecastData() {
        return null;
    }

    @Override
    public ForecastData getForecastDataById(String id) {
        return null;
    }

    @Override
    public ForecastData addOrUpdateForecastData(ForecastData forecastData) {
        return null;
    }

    @Override
    public void deleteForecastData(String id) {

    }
}

