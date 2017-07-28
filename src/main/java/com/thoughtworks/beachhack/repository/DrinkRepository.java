package com.thoughtworks.beachhack.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.Optional;
import java.util.stream.Stream;

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

    public Optional<DrinkStock> getDrinkStock(String drink) {
        try {
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            return Optional.ofNullable(mapper.load(DrinkStock.class, drink));
        } catch (AmazonDynamoDBException e) {
            return Optional.empty();
        }
    }

    public void save(DrinkStock drinkStock) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        try {
            mapper.save(drinkStock);
        } catch (ResourceNotFoundException e) {
            throw new IllegalStateException("Could not save drink stock - does the DynamoDB table 'drink_stock' with hashkey 'drinkName' exist ?");
        }
    }

    public Stream<DrinkStock> loadAll() {
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        DynamoDBScanExpression expression = new DynamoDBScanExpression();
        return mapper.scan(DrinkStock.class, expression).stream();
    }

}
