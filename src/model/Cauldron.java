package model;

import factory.RecipeFactory;
import game.Player;
import utils.ConsoleUI;
import utils.InputManager;

import java.util.*;

public class Cauldron {
    public Inventory inventory;
    private RecipeFactory recipeFactory;
    private Player player;

    public Cauldron(Inventory inventory, RecipeFactory recipeFactory, Player player) {
        this.inventory = inventory;
        this.recipeFactory = recipeFactory;
        this.player = player;
    }

    public void startMixing() {
        inventory.resetMix();
        ConsoleUI.displayIngredients(inventory.getAvailableIngredients(player));
        
        System.out.println("Add ingredients by number (type -1 to brew):");
        List<Ingredient> available = inventory.getAvailableIngredients(player);
        
        boolean mixing = true;
        while (mixing) {
            try {
                int choice = InputManager.readInt();
                
                if (choice == -1) {
                    mixing = false;
                    continue;
                }
                
                if (choice < 1 || choice > available.size()) {
                    System.out.println("Invalid choice.");
                    continue;
                }
                
                Ingredient selected = available.get(choice - 1);
                inventory.addToCauldron(selected);
                
                System.out.println("Added: " + selected.getName());
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number or -1 to brew.");
                InputManager.clearBuffer();
            }
        }
<<<<<<< HEAD
        
        // Once we exit the loop, brew the potion
=======
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
        brewPotion();
    }

    private void brewPotion() {
        List<String> inputIngredients = new ArrayList<>();
        List<Ingredient> cauldronContents = inventory.getIngredientStack();
        
        if (cauldronContents.isEmpty()) {
            System.out.println("⚠️ No ingredients added. Please add ingredients to brew a potion.");
            return;
        }
        
        for (Ingredient ing : cauldronContents) {
            inputIngredients.add(ing.getName());
        }
        Collections.sort(inputIngredients); // Sort ingredients alphabetically

        Potion result = recipeFactory.getPotionByIngredients(inputIngredients);

        if (result != null) {
            System.out.println("✨ Success! You brewed: " + result.getName());
            player.updateScore(result.getDifficulty() * 10); // reward score
            player.addBrewedPotion(result);
            player.checkLevelUp();
<<<<<<< HEAD
            
            // Display potion details
            System.out.println("📊 Potion Details:");
            System.out.println("  Name: " + result.getName());
            System.out.println("  Effect: " + result.getEffect());
            System.out.println("  Difficulty: " + result.getDifficulty());
            System.out.println("  Damage: " + result.getDamage());
=======
        } else if (inputIngredients.isEmpty()) {
            System.out.println("⚠️ No ingredients added. Please add ingredients to brew a potion.");
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
        } else {
            System.out.println("💥 The cauldron explodes! Invalid recipe.");
        }

        inventory.resetMix();
    }
    
    // Helper method to display all available recipes (can be used for hints)
    public void showRecipeBook() {
        System.out.println("\n📖 Recipe Book:");
        List<Recipe> recipes = recipeFactory.getAllRecipes();
        
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }
        
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (player.getLevel() >= recipe.getRequiredLevel()) {
                System.out.println((i + 1) + ". " + recipe.getPotion().getName() + 
                                   " (Requires: " + String.join(", ", recipe.getIngredients()) + ")");
            } else {
                System.out.println((i + 1) + ". ??? (Unlocks at level " + recipe.getRequiredLevel() + ")");
            }
        }
        System.out.println();
    }
}