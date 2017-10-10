package com.thoughtworks.beachhack.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wgauvin on 27/7/17.
 */
public class DrinkStockAlertService {

    private final AmazonSNS client;
    private final String topicArn;

    public DrinkStockAlertService() {
        this.client = AmazonSNSClientBuilder.defaultClient();
        this.topicArn = client.createTopic("low_stock_alert").getTopicArn();
    }

    public void alertLowStockLevel(Map<String, Integer> lowStock, String drinkName, int newStockLevel) {
        String message = String.format("Current stock level for %s is %d \nOther low stock: \n ", drinkName, newStockLevel);
        message += lowStock.entrySet().stream()
                .map(map ->
                    String.format("Drink: %s Quantity: %d\n", map.getKey(), map.getValue()))
                .collect(Collectors.joining());

        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(topicArn)
                .withSubject(String.format("Stock level for %s is low", drinkName))
                .withMessage(String.format(message));

        client.publish(publishRequest);
    }

}
