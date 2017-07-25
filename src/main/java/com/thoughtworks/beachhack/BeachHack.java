package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.thoughtworks.beachhack.model.DrinkInventory;
import com.thoughtworks.beachhack.model.DrinkStock;

import java.util.Map;

public class BeachHack implements RequestHandler<DrinkStock, Map<String, Integer>> {

    private DrinkInventory inventory = new DrinkInventory();

    @Override
    public Map<String, Integer> handleRequest(DrinkStock delta, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Got a request for drink " + delta.getDrinkName() + ": " + delta.getQuantity());

        inventory.updateInventory(delta.getDrinkName(), delta.getQuantity());

        return inventory.getInventoryMap();
    }

}