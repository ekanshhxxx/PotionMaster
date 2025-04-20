package model;

import factory.RecipeFactory;
import game.Player;
import utils.ConsoleUI;

import java.util.*;

public class Cauldron {
    private Inventory inventory;
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

        Scanner sc = new Scanner(System.in);
        System.out.println("Add ingredients by number (type -1 to brew):");

        List<Ingredient> available = inventory.getAvailableIngredients(player);

        while (true) {
            int choice = sc.nextInt();
            if (choice == -1) break;
            if (choice < 1 || choice > available.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            Ingredient selected = available.get(choice - 1);
            inventory.addToCauldron(selected);  // ✅ Correct usage

            System.out.println("Added: " + selected.getName());
        }

        brewPotion();
    }

    private void brewPotion() {
        List<String> inputIngredients = new ArrayList<>();
        for (Ingredient ing : inventory.getIngredientStack()) {
            inputIngredients.add(ing.getName());
        }

        Potion result = recipeFactory.getPotionByIngredients(inputIngredients);

        if (result != null) {
            System.out.println("✨ Success! You brewed: " + result.getName());
            player.updateScore(result.getDifficulty() * 10); // reward score
            player.addBrewedPotion(result);
            player.checkLevelUp();
        } else {
            System.out.println("💥 The cauldron explodes! Invalid recipe.");
        }

        inventory.resetMix();
    }
}
