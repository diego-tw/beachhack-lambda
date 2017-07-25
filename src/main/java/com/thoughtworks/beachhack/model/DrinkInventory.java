package com.thoughtworks.beachhack.model;

import java.util.HashMap;
import java.util.Map;

public class DrinkInventory {

    private Map<String, Integer> drinkStocks = new HashMap<>();

    public Map<String, Integer> getInventoryMap() {
        return drinkStocks;
    }

    public void updateInventory(String drinkName, Integer delta) {
        Integer currentLevel = drinkStocks.getOrDefault(drinkName, 0);
        drinkStocks.put(drinkName, Math.max(currentLevel + delta, 0));
    }

}
