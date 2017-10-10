package com.thoughtworks.beachhack.service;

import com.thoughtworks.beachhack.model.DrinkStock;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class DrinkStockAlertServiceTest {

    DrinkStockAlertService drinkStockAlertService;

    @Ignore
    @Test
    public void shouldPrintOutMessageWithAllDrinks(){
        Map<String, Integer> coke = new HashMap<>();
        coke.put("Coke", 2);

        DrinkStock campariStock = new DrinkStock("Campari", 2, 2);


        drinkStockAlertService = new DrinkStockAlertService();
//        drinkStockAlertService.alertLowStockLevel(coke,campariStock.getDrinkName(), campariStock.getQuantity() );
    }
}
