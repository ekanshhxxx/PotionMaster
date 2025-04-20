package model;

import game.Player;

import java.util.*;

public class Inventory {
    private Stack<Ingredient> ingredientStack = new Stack<>();
    private final List<Ingredient> allIngredients = List.of(
            new Ingredient("Mandrake Root", "Energy Boost"),
            new Ingredient("Dragon Scale", "Fire Resistance"),
            new Ingredient("Moon Dust", "Night Vision"),
            new Ingredient("Phoenix Feather", "Revival"),
            new Ingredient("Wolfsbane", "Strength"),
            new Ingredient("Goblin Ear", "Speed"),             // Unlock at level 2
            new Ingredient("Basilisk Fang", "Poison"),         // Unlock at level 3
            new Ingredient("Mermaid Tears", "Healing")         // Unlock at level 4
    );

    private int maxCapacity;

    public Inventory(int playerLevel) {
        // Cauldron capacity grows with level
        this.maxCapacity = 5 + playerLevel * 2; // Level 1 = 7, Level 2 = 9, etc.
    }

    // Dynamically returns ingredients unlocked at current level
    public List<Ingredient> getAvailableIngredients(Player player) {
        int unlockedCount = Math.min(5 + (player.getLevel() - 1), allIngredients.size());
        return allIngredients.subList(0, unlockedCount);
    }
    public void addToCauldron(Ingredient ing) {
        if (ingredientStack.size() >= maxCapacity) {
            System.out.println("⚠️ Cauldron is full! Max capacity of " + maxCapacity + " reached.");
        } else {
            ingredientStack.push(ing);
        }
    }

    public Stack<Ingredient> getIngredientStack() {
        return ingredientStack;
    }

    public void resetMix() {
        ingredientStack.clear();
    }

    // Optional: In case you need to access all ingredients or for future UI
    public List<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Optional: Reset capacity if player's level changes during runtime
    public void updateCapacity(int playerLevel) {
        this.maxCapacity = 5 + playerLevel * 2;
    }


}
