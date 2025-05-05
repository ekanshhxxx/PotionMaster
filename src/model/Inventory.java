package model;

import java.util.*;

import game.Player;

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
            new Ingredient("Mermaid Tears", "Healing")       // Unlock at level 4
    );

    private int maxCapacity;
    public int potionCapacity; // Default potion capacity

    public Inventory(int playerLevel) {
        // Cauldron capacity grows with level
        this.maxCapacity = 5 + playerLevel * 2; // Level 1 = 7, Level 2 = 9, etc.
        this.potionCapacity = 4 + playerLevel; // Default potion capacity
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
    // public void addPotionToInventory(Potion potion) {
    //     if (potionStack.size() >= potionCapacity) {
    //         System.out.println("⚠️ Inventory is full! Max capacity of " + potionCapacity + " reached.");
    //         return;
    //     }
    //     potionStack.push(potion);
    //     System.out.println("Potion added to inventory: " + potion.getName());
    // }

    // public Potion usePotion() {
    //     if (potionStack.isEmpty()) {
    //         System.out.println("❌ No potions in inventory!");
    //         return null;
    //     }
    //     return potionStack.pop();
    // }

    // public void viewPotions() {
    //     System.out.println("🧪 Your Potions:");
    //     if (potionStack.isEmpty()) {
    //         System.out.println("No potions in inventory.");
    //         return;
    //     }
    //     for (Potion potion : potionStack) {
    //         System.out.println("- " + potion.getName());
    //     }
    // }

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

    public void viewInventory(Player player) {
        System.out.println();
        System.out.println("===============================================================");
        System.out.println("🧺 Your Inventory: ");
        
        // Display Potions
        System.out.println("🧪 Potion Stack:");
        if (player.getBrewedPotions().isEmpty()) {
            System.out.println("No potions in inventory.");
        } else {
            for (Potion potion : player.getBrewedPotions()) {
                System.out.println("- " + potion.getName());
            }
        }
        
        // Display Ingredients
        System.out.println("\n📦 Ingredient Stack:");
        if (ingredientStack.isEmpty()) {
            System.out.println("No ingredients in cauldron.");
        } else {
            for (Ingredient ing : ingredientStack) {
                System.out.println("- " + ing.getName() + " (" + ing.getEffect() + ")");
            }
        }
        
        System.out.println("===============================================================");
    }

    public void viewIngredientStack() {
        System.out.println("\n🧪 Ingredient Stack: ");
        if (ingredientStack.isEmpty()) {
            System.out.println("No ingredients in cauldron.");
            return;
        }
        for (Ingredient ing : ingredientStack) {
            System.out.println("- " + ing.getName() + " (" + ing.getEffect() + ")");
        }
        System.out.println();
    }
}
