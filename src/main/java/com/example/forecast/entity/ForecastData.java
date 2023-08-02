package com.example.forecast.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "forecast_data")
@Data
public class ForecastData {
    @Id
    private String id;
    @Field("product_id")
    private String productId;
    @Field(value = "site_id")
    private String siteId;

    @Field(value = "forecast_file_id", targetType = FieldType.OBJECT_ID)
    private String forecastFileId;

    @Field("forecast_date")
    private Date date;
    @Field("quantity")
    private Integer quantity;
    @Field("status")
    private String status;
    @Field("created_at")
    private Date createdAt;
    @Field("created_by")
    private String createdBy;
    @Field("last_updated_at")
    private Date lastUpdatedAt;
    @Field("last_updated_by")
    private String lastUpdatedBy;

    // getters and setters
}

