package com.springdata.demo.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> findUsersWithAdditionalData(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("toString(_id)").as("id")
                        .andExpression("'**********'").as("pwd")
                        .andExclude("_id")
                        .andInclude("username", "roles"),
                Aggregation.lookup(
                        "Additional_data",
                        "id",
                        "login_user_id",
                        "additionalData"
                )
        );
        return mongoTemplate.aggregate(aggregation, "users", Document.class).getMappedResults();
    }

    public List<Document> findUsersWithAdditionalData(String id){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(id)),
                Aggregation.project()
                        .andExpression("toString(_id)").as("id")
                        .andExpression("'**********'").as("pwd")
                        .andExclude("_id")
                        .andInclude("username", "roles"),
                Aggregation.lookup(
                        "Additional_data",
                        "id",
                        "login_user_id",
                        "additionalData"
                )
        );
        return mongoTemplate.aggregate(aggregation, "users", Document.class).getMappedResults();
    }
}
