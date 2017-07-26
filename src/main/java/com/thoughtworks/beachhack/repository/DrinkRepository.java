package com.thoughtworks.beachhack.repository;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
