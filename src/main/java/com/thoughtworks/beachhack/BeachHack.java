package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.thoughtworks.beachhack.model.DrinkStock;
import com.thoughtworks.beachhack.model.LogLevel;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.service.DrinkStockAlertService;
import com.thoughtworks.beachhack.service.LogService;

import java.util.Map;
import java.util.stream.Collectors;

public class BeachHack implements RequestHandler<DrinkStock, Map<String, Integer>> {

    private final DrinkInventory inventory;
    private final DrinkStockAlertService alertService;
//    private final LogService logService;


    public BeachHack() {
        this(new DrinkInventory(), new DrinkStockAlertService(), new LogService());
    }

    public BeachHack(DrinkInventory inventory, DrinkStockAlertService alertService, LogService logService) {
        this.inventory = inventory;
        this.alertService = alertService;
//        this.logService = logService;
    }

    @Override
    public Map<String, Integer> handleRequest(DrinkStock delta, Context context) {

//        logService.info("Got a stock change for drink " + delta.getDrinkName() + " changed by " + delta.getQuantity());

        if (delta.getDrinkName() == null || delta.getQuantity() == null) {
//            logService.error("Null value of drinkName or quantity: no update");
        } else {
//            logService.info("Checking stock level to see if we need to raise a low-stock alert");
            DrinkStock drinkStock = inventory.getDrinkStock(delta.getDrinkName());
            checkStockLevelIfNeeded(inventory.getInventoryMap(),drinkStock, delta.getQuantity());
            inventory.updateInventory( delta.getDrinkName(), delta.getQuantity());
        }

//        logService.info("Returning full inventory: " + inventory.getInventoryMap());
        return inventory.getInventoryMap();
    }


    protected void checkStockLevelIfNeeded(Map<String, Integer> inventory, DrinkStock drinkStock, int levelChange) {
        if (drinkStock == null) {
            return;
        }

        Map<String, Integer> lowStock = inventory.entrySet().stream()
                .map(item ->  this.inventory.getDrinkStock(item.getKey()))
                .filter(item -> item.getQuantity() <= item.getAlertThreshold())
                .collect(Collectors.toMap(drink -> drink.getDrinkName(), drink -> drink.getQuantity()));

        if (drinkStock.getQuantity() > drinkStock.getAlertThreshold() && drinkStock.getQuantity() + levelChange <= drinkStock.getAlertThreshold()) {
            alertService.alertLowStockLevel(lowStock, drinkStock.getDrinkName(), drinkStock.getQuantity() + levelChange);
//            logService.warn("Low stock alert for drink " + drinkStock.getDrinkName() + ": " + (drinkStock.getQuantity() + levelChange) + " remaining");
        }
    }

}
