package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.thoughtworks.beachhack.model.DrinkStock;
import com.thoughtworks.beachhack.model.LogLevel;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.service.DrinkStockAlertService;
import com.thoughtworks.beachhack.service.LogService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BeachHackTest {

    @Mock
    private Context context;

    @Mock
    private DrinkInventory drinkInventory;

    @Mock
    private DrinkStockAlertService alertService;

    @Mock
    private LogService logService;

    private BeachHack beachHack;

    Map<String, Integer> inventoryMock;

    @Before
    public void setUp() throws Exception {
        inventoryMock = new HashMap<>();
        inventoryMock.put("Midori", 10);
        inventoryMock.put("Coke", 2);
        when(drinkInventory.getInventoryMap()).thenReturn(inventoryMock);
        when(drinkInventory.getDrinkStock("Midori")).thenReturn(new DrinkStock("Midori", 10));

        beachHack = new BeachHack(drinkInventory, alertService, logService);
    }

    @Test
    public void handleRequestDoesUpdateInventory() throws Exception {
        Map<String, Integer> result = beachHack.handleRequest(new DrinkStock("Midori", 10), context);
        assertThat(result, is(inventoryMock));
        verify(drinkInventory).updateInventory("Midori", 10);
    }

    @Test
    public void handleRequestDoesCheckStockLevel_whenDecreasingOntoThreshold() throws Exception {
        validateStockLevelCheckRule(3, -1, 2, true);
    }


    @Test
    public void handleRequestDoesCheckStockLevel_whenDecreasingBelowThreshold() throws Exception {
        validateStockLevelCheckRule(3, -2, 2, true);
    }

    @Test
    public void handleRequestDoesNotCheckStockLevel_whenIncreasingOntoThreshold() throws Exception {
        validateStockLevelCheckRule(1, 1, 2, false);
    }

    @Test
    public void handleRequestDoesNotCheckStockLevel_whenIncreasingPastThreshold() throws Exception {
        validateStockLevelCheckRule(1, 2, 2, false);
    }

    @Test
    public void handleRequestDoesNotCheckStockLevel_whenNewDrink() throws Exception {

        when(drinkInventory.getDrinkStock(anyString())).thenReturn(null);

        beachHack.handleRequest(new DrinkStock("TestDrink", 1), context);

        verify(alertService, never()).alertLowStockLevel(any(),any(), anyInt());
    }

    @Ignore("Need to implement the logging service")
    @Test
    public void handleRequestShouldLogRequestToChangeDrinkState() throws Exception {
        beachHack.handleRequest(new DrinkStock("TestDrink", 1), context);

        verify(logService).info("Got a stock change for drink TestDrink changed by 1");
    }

    @Ignore("Need to implement the logging service")
    @Test
    public void handleRequestShouldLogErrorWithNullDrinkName() throws Exception {
        beachHack.handleRequest(new DrinkStock(null, 1), context);

        verify(logService).error("Null value of drinkName or quantity: no update");
    }

    private void validateStockLevelCheckRule(int currentStockLevel, int increase, int alertThreshold, boolean shouldRaiseAlert) throws Exception {
        DrinkStock campariStock = new DrinkStock("Campari", currentStockLevel, alertThreshold);

        when(drinkInventory.getDrinkStock(campariStock.getDrinkName())).thenReturn(campariStock);

        beachHack.handleRequest(new DrinkStock(campariStock.getDrinkName(), increase), context);

        Map<String, Integer> coke = new HashMap<>();
        coke.put("Coke", 2);

        if (shouldRaiseAlert) {
            verify(alertService).alertLowStockLevel(coke, campariStock.getDrinkName(), campariStock.getQuantity() + increase);
//            verify(logService).warn("Low stock alert for drink Campari: " + (campariStock.getQuantity() + increase) + " remaining");
        } else {
            verify(alertService, never()).alertLowStockLevel(any(), any(), anyInt());
        }
    }

}
