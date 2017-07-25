package com.thoughtworks.beachhack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.thoughtworks.beachhack.model.DrinkStock;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeachHackTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        LambdaLogger logger = mock(LambdaLogger.class);
        context = mock(Context.class);
        when(context.getLogger()).thenReturn(logger);
    }

    @Test
    public void handleRequest() throws Exception {

        BeachHack beachHack = new BeachHack();

        Map<String, Integer> inventory = beachHack.handleRequest(new DrinkStock("Midori", 10), context);

        assertEquals((Integer) 10, inventory.get("Midori"));

    }

}