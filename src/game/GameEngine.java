package game;

import model.*;
import factory.RecipeFactory;
import utils.ConsoleUI;

public class GameEngine {
    private Player player;
    private Inventory inventory;
    private Cauldron cauldron;
    private RecipeFactory recipeFactory;

    public void start() {
        ConsoleUI.showWelcome();
        player = new Player(ConsoleUI.getPlayerName());
        Inventory inventory = new Inventory(player.getLevel());

        recipeFactory = new RecipeFactory();
        cauldron = new Cauldron(inventory, recipeFactory, player);


        boolean playing = true;
        while (playing) {
            ConsoleUI.showMenu();
            int choice = ConsoleUI.getChoice();
            switch (choice) {
                case 1 -> cauldron.startMixing();
                case 2 -> player.viewStats();
                case 3 -> playing = false;
                case 4 -> player.viewBrewedPotions();

                default -> System.out.println("Invalid choice!");
            }
        }

        ConsoleUI.showExit();
    }
}