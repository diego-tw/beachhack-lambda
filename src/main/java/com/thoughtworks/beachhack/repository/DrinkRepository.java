package com.thoughtworks.beachhack.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgauvin on 26/7/17.
 */
public class DrinkRepository {

    private final AmazonDynamoDB client;

    public DrinkRepository() {
        client = AmazonDynamoDBClientBuilder
                .standard()
                .build();
    }

    public List<DrinkStock> listDrinks() {
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        return new ArrayList<>(mapper.scan(DrinkStock.class, new DynamoDBScanExpression()));
    }

}
