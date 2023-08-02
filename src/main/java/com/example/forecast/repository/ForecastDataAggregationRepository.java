package com.example.forecast.repository;

// Requires official Java MongoDB Driver 4.0+

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class ForecastDataAggregationRepository {


    @Autowired
    MongoClient client;

//    public void procees() {
//        try {
//            MongoDatabase database = client.getDatabase("ocado_forecast");
//            MongoCollection<Document> collection = database.getCollection("forecast_data");
//
//            // Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/
//
//            Consumer<Document> processBlock = new Consumer<Document>() {
//                @Override
//                public void accept(Document document) {
//                    System.out.println(document);
//                }
//            };
//
//            List<? extends Bson> pipeline = Arrays.asList(
//                    new Document()
//                            .append("$lookup", new Document()
//                                    .append("from", "sites")
//                                    .append("localField", "site_id")
//                                    .append("foreignField", "_id")
//                                    .append("as", "site")
//                            ),
//                    new Document()
//                            .append("$unwind", new Document()
//                                    .append("path", "$site")
//                            ),
//                    new Document()
//                            .append("$lookup", new Document()
//                                    .append("from", "forecast_file")
//                                    .append("localField", "forecat_file_id")
//                                    .append("foreignField", "_id")
//                                    .append("as", "forecast_file")
//                            ),
//                    new Document()
//                            .append("$unwind", new Document()
//                                    .append("path", "$forecast_file")
//                            ),
//                    new Document()
//                            .append("$match", new Document()
//                                    .append("$or", Arrays.asList(
//                                                    new Document()
//                                                            .append("forecast_file.FEED_TYPE", "ONLINE"),
//                                                    new Document()
//                                                            .append("$and", Arrays.asList(
//                                                                            new Document()
//                                                                                    .append("forecast_file.FEED_TYPE", "RDF"),
//                                                                            new Document()
//                                                                                    .append("site.disable_ind_rdf", "A")
//                                                                    )
//                                                            )
//                                            )
//                                    )
//                                    .append("forecast_file.is_deleted", false)
//                                    .append("data", new Document()
//                                            .append("$elemMatch", new Document()
//                                                    .append("date", new Document()
//                                                            .append("$gte", "04-18-2023")
//                                                            .append("$lte", "05-15-2023")
//                                                    )
//                                            )
//                                    )
//                                    .append("created_at", new Document()
//                                            .append("$gte", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").parse("2023-01-17 05:30:00.000+0530"))
//                                    )
//                            ),
//                    new Document()
//                            .append("$sort", new Document()
//                                    .append("forecast_file.is_sticky", -1.0)
//                                    .append("forecast_file.priority", -1.0)
//                                    .append("created_at", -1.0)
//                            ),
//                    new Document()
//                            .append("$unwind", new Document()
//                                    .append("path", "$data")
//                                    .append("includeArrayIndex", "index")
//                                    .append("preserveNullAndEmptyArrays", true)
//                            ),
//                    new Document()
//                            .append("$group", new Document()
//                                    .append("_id", new Document()
//                                            .append("product_id", "$product_id")
//                                            .append("site_id", "$site_id")
//                                            .append("date", "$data.date")
//                                    )
//                                    .append("quantity", new Document()
//                                            .append("$first", "$data.quantity")
//                                    )
//                            ),
//                    new Document()
//                            .append("$sort", new Document()
//                                    .append("_id.product_id", 1.0)
//                                    .append("_id.site_id", 1.0)
//                                    .append("_id.date", 1.0)
//                            ),
//                    new Document()
//                            .append("$group", new Document()
//                                    .append("_id", new Document()
//                                            .append("product_id", "$_id.product_id")
//                                            .append("site_id", "$_id.site_id")
//                                    )
//                                    .append("data", new Document()
//                                            .append("$push", new Document()
//                                                    .append("date", "$_id.date")
//                                                    .append("quantity", "$quantity")
//                                            )
//                                    )
//                            ),
//                    new Document()
//                            .append("$match", new Document()
//                                    .append("$expr", new Document()
//                                            .append("$eq", Arrays.asList(
//                                                            new Document()
//                                                                    .append("$size", "$data"),
//                                                            28.0
//                                                    )
//                                            )
//                                    )
//                            ),
//                    new Document()
//                            .append("$sort", new Document()
//                                    .append("_id.product_id", 1.0)
//                                    .append("_id.site_id", 1.0)
//                            )
//            );
//
//            collection.aggregate(pipeline)
//                    .allowDiskUse(false)
//                    .forEach(processBlock);
//
//        } catch (MongoException e) {
//            // handle MongoDB exception
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void procees() {
        try {
            MongoDatabase database = client.getDatabase("ocado_forecast");
            MongoCollection<Document> collection = database.getCollection("forecast_data");

            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println(document);
                }
            };


            List<? extends Bson> pipeline = Arrays.asList(new Document("$lookup",
                            new Document("from", "sites")
                                    .append("localField", "site_id")
                                    .append("foreignField", "_id")
                                    .append("as", "site")),
                    new Document("$unwind",
                            new Document("path", "$site")),
                    new Document("$lookup",
                            new Document("from", "forecast_file")
                                    .append("localField", "forecast_file_id")
                                    .append("foreignField", "_id")
                                    .append("as", "forecast_file")),
                    new Document("$unwind",
                            new Document("path", "$forecast_file")),
                    new Document("$match",
                            new Document("$or", Arrays.asList(new Document("forecast_file.FEED_TYPE", "ONLINE"),
                                    new Document("$and", Arrays.asList(new Document("forecast_file.FEED_TYPE", "RDF"),
                                            new Document("site.disable_ind_rdf", "A")))))
                                    .append("forecast_file.is_deleted", false)
                                    .append("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("forecast_file.status", "S"),
                                                    new Document("status", "N"),
                                                    new Document("forecast_file.created_at",
                                                            new Document("$gte",
                                                                    new java.util.Date(1685491200000L))))),
                                            new Document("$and", Arrays.asList(new Document("forecast_file.status", "P"),
                                                    new Document("status", "N"))),
                                            new Document("$and", Arrays.asList(new Document("forecast_file.is_sticky", true),
                                                    new Document("forecast_file.sticky_end_date",
                                                            new Document("$lte",
                                                                    new java.util.Date(1685491200000L))),
                                                    new Document("status",
                                                            new Document("$nin", Arrays.asList("X")))))))
                                    .append("forecast_date",
                                            new Document("$gte",
                                                    new java.util.Date(1685577600000L)))
                                    .append("forecast_date",
                                            new Document("$lte",
                                                    new java.util.Date(1687996800000L)))),
                    new Document("$sort",
                            new Document("forecast_file.is_sticky", -1L)
                                    .append("forecast_file.priority", -1L)
                                    .append("forecast_file.id", -1L)
                                    .append("created_at", -1L)),
                    new Document("$group",
                            new Document("_id",
                                    new Document("product_id", "$product_id")
                                            .append("site_id", "$site_id")
                                            .append("date", "$forecast_date"))
                                    .append("quantity",
                                            new Document("$first", "$quantity"))),
                    new Document("$sort",
                            new Document("_id.product_id", 1L)
                                    .append("_id.site_id", 1L)
                                    .append("_id.date", 1L)),
                    new Document("$group",
                            new Document("_id",
                                    new Document("product_id", "$_id.product_id")
                                            .append("site_id", "$_id.site_id"))
                                    .append("data",
                                            new Document("$push",
                                                    new Document("date", "$_id.date")
                                                            .append("quantity", "$quantity")))),
                    new Document("$match",
                            new Document("data",
                                    new Document("$size", 28L))),
                    new Document("$sort",
                            new Document("_id.product_id", 1L)
                                    .append("_id.site_id", 1L)));

            collection.aggregate(pipeline)
                    .allowDiskUse(false)
                    .forEach(processBlock);

        } catch (MongoException e) {
            // handle MongoDB exception
        }
    }

}
