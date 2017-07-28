package com.thoughtworks.beachhack.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "drink_stock")
public class DrinkStock {

    public static final int DEFAULT_ALERT_THRESHOLD = 1;

    private String drinkName;
    private Integer quantity;
    private Integer alertThreshold = DEFAULT_ALERT_THRESHOLD;

    public DrinkStock() {
    }

    public DrinkStock(final String drinkName, final Integer quantity) {
        this.drinkName = drinkName;
        this.quantity = quantity;
    }

    public DrinkStock(final String drinkName, final Integer quantity, final Integer alertThreshold) {
        this.drinkName = drinkName;
        this.quantity = quantity;
        this.alertThreshold = alertThreshold;
    }

    @DynamoDBHashKey(attributeName = "drink_name")
    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(final String drinkName) {
        this.drinkName = drinkName;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAlertThreshold() {
      return alertThreshold;
    }

    @Override
    public String toString() {
        return "DrinkStock{" +
                "drinkName='" + drinkName + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
