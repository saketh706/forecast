package com.example.forecast.entity;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "forecast_file")
@Data
public class ForecastFile {
    @Id
    private String id;

    @Field("file_name")
    private String fileName;
    @Field("file_link")
    private String fileLink;
    @Field("error_file_link")
    private String errorFileLink;
    @Field("priority")
    private int priority;
    @Field("total_rows")
    private int totalRows;
    @Field("total_errors")
    private int totalErrors;
    @Field("FEED_TYPE")
    private String feedType;
    @Field("status")
    private String status;
    @Field("is_deleted")
    private Boolean isDeleted;
    @Field("is_sticky")
    private Boolean isSticky;
    @Field("sticky_start_date")
    private Date stickyStartDate;

    @Field("sticky_end_date")
    private Date stickyEndDate;
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
