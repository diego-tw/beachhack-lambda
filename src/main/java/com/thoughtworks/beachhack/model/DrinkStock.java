package com.thoughtworks.beachhack.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "drink_stock")
public class DrinkStock {

    private String drinkName;
    private Integer quantity;

    public DrinkStock() {
    }

    public DrinkStock(final String drinkName, final Integer quantity) {
        this.drinkName = drinkName;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "DrinkStock{" +
                "drinkName='" + drinkName + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
