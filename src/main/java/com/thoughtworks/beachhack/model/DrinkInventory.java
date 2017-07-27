package com.thoughtworks.beachhack.model;

import com.thoughtworks.beachhack.repository.DrinkRepository;

import java.util.Map;
import java.util.Optional;

import static java.lang.Math.max;
import static java.util.stream.Collectors.toMap;

public class DrinkInventory {

    private final DrinkRepository drinkRepository;

    public DrinkInventory() {
        this(new DrinkRepository());
    }

    public DrinkInventory(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public Map<String, Integer> getInventoryMap() {
        return drinkRepository.loadAll().collect(toMap(DrinkStock::getDrinkName, DrinkStock::getQuantity));
    }

    public void updateInventory(String drinkName, Integer delta) {
        Optional<DrinkStock> drinkStock = drinkRepository.getDrinkStock(drinkName);

        DrinkStock newStock = drinkStock
                .map(curr -> incrementStock(curr, delta))
                .orElse(new DrinkStock(drinkName, delta));

        drinkRepository.save(newStock);
    }

    private DrinkStock incrementStock(DrinkStock current, int delta) {
        int quantity = current.getQuantity() + delta;
        return new DrinkStock(current.getDrinkName(), max(0, quantity));
    }

}
