package com.thoughtworks.beachhack.service;

import static java.util.Optional.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.thoughtworks.beachhack.model.DrinkStock;
import com.thoughtworks.beachhack.repository.DrinkRepository;



@RunWith(MockitoJUnitRunner.class)
public class DrinkInventoryTest {



    private DrinkInventory inventory;

    @Mock
    private DrinkRepository drinkRepository;



    @Before
    public void setup() {
        inventory = new DrinkInventory(drinkRepository);
    }

    @Test
    public void updateInventory_shouldCreateNonExistentDrinks() throws Exception {
        when(drinkRepository.getDrinkStock("Kahlua")).thenReturn(empty());

        inventory.updateInventory("Kahlua", 5);

        verify(drinkRepository).getDrinkStock("Kahlua");
        verify(drinkRepository).save(argThat(samePropertyValuesAs(new DrinkStock("Kahlua", 5))));
    }

    @Test
    public void updateInventory_shouldIncreaseStock() throws Exception {
        when(drinkRepository.getDrinkStock("Scotch")).thenReturn(Optional.of(new DrinkStock("Scotch", 3)));

        inventory.updateInventory("Scotch", 2);

        verify(drinkRepository).save(argThat(samePropertyValuesAs(new DrinkStock("Scotch", 5))));
    }

    @Test
    public void updateInventory_shouldDecreaseStock() throws Exception {
        when(drinkRepository.getDrinkStock("Midori")).thenReturn(Optional.of(new DrinkStock("Midori", 10)));

        inventory.updateInventory("Midori", -3);

        verify(drinkRepository).save(argThat(samePropertyValuesAs(new DrinkStock("Midori", 7))));
    }

    @Test
    public void updateInventory_shouldNeverBeNegative() throws Exception {
        when(drinkRepository.getDrinkStock("CoconutWater")).thenReturn(Optional.of(new DrinkStock("CoconutWater", 4)));

        inventory.updateInventory("CoconutWater", -6);

        verify(drinkRepository).save(argThat(samePropertyValuesAs(new DrinkStock("CoconutWater", 0))));
    }

    @Test
    public void getInventoryMap_shouldReturnCurrentStockFromDatabase() {
        when(drinkRepository.loadAll()).thenReturn(Stream.of(new DrinkStock("Tiger Beer", 10), new DrinkStock("Apple Cider", 2)));

        Map<String, Integer> result = inventory.getInventoryMap();

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Tiger Beer", 10);
        expected.put("Apple Cider", 2);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getInventory() {
        when(drinkRepository.loadAll()).thenReturn(Stream.of(new DrinkStock("Tiger Beer", 10), new DrinkStock("Apple Cider", 2)));
        Map<String, DrinkStock> inv = inventory.getInventory();
        assertNotNull(inv);
        assertTrue(!inv.isEmpty());
    }

    @Test
    public void getInventory_shouldReturnStockWithDefaultThresholdsForNewDrinks() {
        when(drinkRepository.getDrinkStock("Midori")).thenReturn(Optional.of(new DrinkStock("Midori", 10)));
        assertNotNull(drinkRepository.getDrinkStock("Midori").get() );
        assertTrue(drinkRepository.getDrinkStock("Midori").get().getAlertThreshold() == DrinkStock.DEFAULT_ALERT_THRESHOLD);
    }

    @Test
    public void getInventory_shouldReturnStockWithThresholdsForExistingDrinks() {
        when(drinkRepository.getDrinkStock("Midori")).thenReturn(Optional.of(new DrinkStock("Midori", 10, 2)));
        assertNotNull(drinkRepository.getDrinkStock("Midori").get() );
        assertNotNull(drinkRepository.getDrinkStock("Midori").get().getAlertThreshold() );
    }
}