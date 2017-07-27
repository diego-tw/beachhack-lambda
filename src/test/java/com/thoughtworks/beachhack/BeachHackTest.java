package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.thoughtworks.beachhack.service.DrinkInventory;
import com.thoughtworks.beachhack.model.DrinkStock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BeachHackTest {

    @Mock
    private Context context;

    @Mock
    private DrinkInventory drinkInventory;

    @Mock
    private LambdaLogger logger;

    private BeachHack beachHack;

    @Before
    public void setUp() throws Exception {
        when(context.getLogger()).thenReturn(logger);

        beachHack = new BeachHack(drinkInventory);
    }

    @Test
    public void handleRequest() throws Exception {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Midori", 10);
        when(drinkInventory.getInventoryMap()).thenReturn(inventory);

        Map<String, Integer> result = beachHack.handleRequest(new DrinkStock("Midori", 10), context);

        assertThat(result, is(inventory));
        verify(drinkInventory).updateInventory("Midori", 10);
    }

}
