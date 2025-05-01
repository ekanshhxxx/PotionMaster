package game;

import model.*;
import factory.RecipeFactory;
import utils.ConsoleUI;

public class GameEngine {
    private Player player;
    public Inventory inventory;
    private Cauldron cauldron;
    private RecipeFactory recipeFactory;

    public void start() {
        ConsoleUI.showWelcome();
        player = new Player(ConsoleUI.getPlayerName());
        Inventory inventory = new Inventory(player.getLevel());
        this.inventory = inventory; // Initialize inventory with player level
        ConsoleUI.displayIngredients(inventory.getAvailableIngredients(player)); // Show available ingredients

        recipeFactory = new RecipeFactory();
        cauldron = new Cauldron(inventory, recipeFactory, player);


        boolean playing = true;
        while (playing) {
            ConsoleUI.showMenu();
            int choice = ConsoleUI.getChoice();
            switch (choice) {
                case 1 -> cauldron.startMixing();
                case 2 -> player.viewStats();
                case 3 -> player.viewBrewedPotions();
                case 5 -> inventory.viewIngredientStack();
                case 6 -> {
                    boolean inGameMenu = true;
                    while (inGameMenu) {
                        ConsoleUI.GameMenu();
                        int gameMenuChoice = ConsoleUI.getChoice();
                        switch (gameMenuChoice) {
                            case 1 -> player.fight(); // Fight with a monster
                            case 2 -> player.GameStats(); 
                            case 3 -> inGameMenu = false; // Exit game menu
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                    ConsoleUI.showMenu(); // Show main menu after exiting game menu
                }
                case 7 -> playing = false;

                default -> System.out.println("Invalid choice!");
            }
        }

        ConsoleUI.showExit();
    }
}