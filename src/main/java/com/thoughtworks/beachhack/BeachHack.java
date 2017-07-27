package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.Map;

public class BeachHack implements RequestHandler<DrinkStock, Map<String, Integer>> {

    private final DrinkInventory inventory;

    public BeachHack() {
        this.inventory = new DrinkInventory();
    }

    public BeachHack(DrinkInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Map<String, Integer> handleRequest(DrinkStock delta, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Got a request for drink " + delta.getDrinkName() + ": " + delta.getQuantity());

        if (delta.getDrinkName() == null || delta.getQuantity() == null) {
            logger.log("Null value of drinkName or quantity: no update");
        } else {
            inventory.updateInventory(delta.getDrinkName(), delta.getQuantity());
        }

        logger.log("Returning inventory: " + inventory.getInventoryMap());
        logger.log("Just testing deploy");
        
        return inventory.getInventoryMap();
    }

}
