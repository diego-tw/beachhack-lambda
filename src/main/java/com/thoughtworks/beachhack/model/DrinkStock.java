package com.thoughtworks.beachhack.model;

public class DrinkStock {
    private String drinkName;
    private Integer quantity;

    public DrinkStock() {
    }

    public DrinkStock(final String drinkName, final Integer quantity) {
        this.drinkName = drinkName;
        this.quantity = quantity;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(final String drinkName) {
        this.drinkName = drinkName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
