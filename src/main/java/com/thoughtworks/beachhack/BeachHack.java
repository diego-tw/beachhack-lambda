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

public class BeachHack implements RequestHandler<DrinkStock, Map<String, Integer>> {

    private final DrinkInventory inventory;
    private final DrinkStockAlertService alertService;
    private final LogService logService;


    public BeachHack() {
        this(new DrinkInventory(), new DrinkStockAlertService(), new LogService());
    }

    public BeachHack(DrinkInventory inventory, DrinkStockAlertService alertService, LogService logService) {
        this.inventory = inventory;
        this.alertService = alertService;
        this.logService = logService;
    }

    @Override
    public Map<String, Integer> handleRequest(DrinkStock delta, Context context) {

        //logService.info
        logService.log(LogLevel.INFO, "Got a stock change for drink " + delta.getDrinkName() + " changed by " + delta.getQuantity());

        if (delta.getDrinkName() == null || delta.getQuantity() == null) {
            logService.log(LogLevel.INFO,"Null value of drinkName or quantity: no update");
        } else {
            logService.log(LogLevel.INFO,"Checking stock level to see if we need to raise a low-stock alert");
            DrinkStock drinkStock = inventory.getDrinkStock(delta.getDrinkName());
            checkStockLevelIfNeeded(drinkStock, delta.getQuantity());
            inventory.updateInventory(delta.getDrinkName(), delta.getQuantity());
        }

        logService.log(LogLevel.INFO,"Returning full inventory: " + inventory.getInventoryMap());
        return inventory.getInventoryMap();
    }


    protected void checkStockLevelIfNeeded(DrinkStock drinkStock, int levelChange) {
        if (drinkStock == null) {
            return;
        }
        if (drinkStock.getQuantity() > drinkStock.getAlertThreshold() && drinkStock.getQuantity() + levelChange <= drinkStock.getAlertThreshold()) {
            alertService.alertLowStockLevel(drinkStock.getDrinkName(), drinkStock.getQuantity() + levelChange);
        }
    }

}
