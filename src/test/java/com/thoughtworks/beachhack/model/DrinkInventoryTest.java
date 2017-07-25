package com.thoughtworks.beachhack.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrinkInventoryTest {

    private DrinkInventory inventory;

    @Before
    public void setup() {
        inventory = new DrinkInventory();
    }

    @Test
    public void updateInventory_shouldCreateNonExistentDrinks() throws Exception {
        assertFalse(inventory.getInventoryMap().containsKey("Kahlua"));
        inventory.updateInventory("Kahlua", 5);
        assertEquals((Integer) 5, inventory.getInventoryMap().get("Kahlua"));
    }

    @Test
    public void updateInventory_shouldIncreaseStock() throws Exception {
        inventory.updateInventory("Scotch", 3);
        assertEquals((Integer) 3, inventory.getInventoryMap().get("Scotch"));
        inventory.updateInventory("Scotch", 2);
        assertEquals((Integer) 5, inventory.getInventoryMap().get("Scotch"));
    }

    @Test
    public void updateInventory_shouldDecreaseStock() throws Exception {
        inventory.updateInventory("Midori", 10);
        assertEquals((Integer) 10, inventory.getInventoryMap().get("Midori"));
        inventory.updateInventory("Midori", -3);
        assertEquals((Integer) 7, inventory.getInventoryMap().get("Midori"));
    }

    @Test
    public void updateInventory_shouldNeverBeNegative() throws Exception {
        inventory.updateInventory("CoconutWater", 4);
        inventory.updateInventory("CoconutWater", -6);
        assertEquals((Integer) 0, inventory.getInventoryMap().get("CoconutWater"));
    }
}