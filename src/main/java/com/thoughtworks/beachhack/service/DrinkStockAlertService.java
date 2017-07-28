package com.thoughtworks.beachhack.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.thoughtworks.beachhack.model.DrinkStock;

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

    public void alertLowStockLevel(DrinkStock stock) {
        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(topicArn)
                .withSubject(String.format("Stock level for %s is low", stock.getDrinkName()))
                .withMessage(String.format("Current stock level for %s is %d", stock.getDrinkName(), stock.getQuantity()));

        client.publish(publishRequest);
    }

}