package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.thoughtworks.beachhack.model.DrinkStock;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.service.DrinkStockAlertService;
import org.junit.Before;
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
    private LambdaLogger logger;

    private BeachHack beachHack;

    @Before
    public void setUp() throws Exception {
        when(context.getLogger()).thenReturn(logger);

        beachHack = new BeachHack(drinkInventory, alertService);
    }

    @Test
    public void handleRequestDoesUpdateInventory() throws Exception {
        Map<String, Integer> inventoryMock = new HashMap<>();
        inventoryMock.put("Midori", 10);
        when(drinkInventory.getInventoryMap()).thenReturn(inventoryMock);
        when(drinkInventory.getDrinkStock("Midori")).thenReturn(new DrinkStock("Midori", 10));

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


    private void validateStockLevelCheckRule(int currentStockLevel, int increase, int alertTreshold, boolean shouldRaiseAlert) throws Exception {
        Map<String, DrinkStock> inventory = new HashMap<>();
        DrinkStock campariStock = new DrinkStock("Campari", currentStockLevel, alertTreshold);
        inventory.put(campariStock.getDrinkName(), campariStock);

        when(drinkInventory.getDrinkStock(campariStock.getDrinkName())).thenReturn(campariStock);

        beachHack.handleRequest(new DrinkStock(campariStock.getDrinkName(), increase), context);

        if (shouldRaiseAlert) {
            verify(alertService).alertLowStockLevel(campariStock.getDrinkName(), campariStock.getQuantity()+increase);
        }
        else {
            verify(alertService, never()).alertLowStockLevel(any(),anyInt());
        }
    }

}
