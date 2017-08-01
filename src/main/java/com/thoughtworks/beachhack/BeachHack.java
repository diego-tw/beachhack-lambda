package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.thoughtworks.beachhack.model.DrinkStock;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.service.DrinkStockAlertService;

import java.util.Map;

public class BeachHack implements RequestHandler<DrinkStock, Map<String, Integer>> {

    private final DrinkInventory inventory;
    private final DrinkStockAlertService alertService;


    public BeachHack() {
        this.inventory = new DrinkInventory();
        this.alertService = new DrinkStockAlertService();
    }

    public BeachHack(DrinkInventory inventory, DrinkStockAlertService alertService) {
        this.inventory = inventory;
        this.alertService = alertService;
    }

    @Override
    public Map<String, Integer> handleRequest(DrinkStock delta, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Got a stock change for drink " + delta.getDrinkName() + " changed by " + delta.getQuantity());

        if (delta.getDrinkName() == null || delta.getQuantity() == null) {
            logger.log("Null value of drinkName or quantity: no update");
        } else {
            logger.log("Checking stock level to see if we need to raise a low-stock alert");
            DrinkStock drinkStock = inventory.getDrinkStock(delta.getDrinkName());
            checkStockLevelIfNeeded(drinkStock, delta.getQuantity());
            inventory.updateInventory(delta.getDrinkName(), delta.getQuantity());
        }

        logger.log("Returning full inventory: " + inventory.getInventoryMap());
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
