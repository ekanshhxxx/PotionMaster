package game;

import model.*;
import factory.RecipeFactory;
import utils.ConsoleUI;
import main.account.PlayerAccount;
import utils.InputManager;

import DSA.*;

public class GameEngine {
    private Player player;
    public Inventory inventory;
    private Cauldron cauldron;
    private RecipeFactory recipeFactory;
    private PlayerAccount playerAccount;
    private main.account.AccountManager accountManager;
    
    // Default constructor for backward compatibility
    public GameEngine() {
        // Default constructor
    }
    
    // Constructor that accepts PlayerAccount for login functionality
    public GameEngine(PlayerAccount account, main.account.AccountManager accountManager) {
        this.playerAccount = account;
        this.accountManager = accountManager;
    }

    public void start() {
        ConsoleUI.showWelcome();
        
        if (playerAccount != null) {
            // Create player using account data
            player = new Player(playerAccount.getUsername());
            player.updateScore(playerAccount.getXp());
            player.setLevel(playerAccount.getLevel());
            player.Creads = playerAccount.getCredits();
            player.HealthLevel = playerAccount.getHealthPoints();
            player.setGameLevel(playerAccount.getGameLevel());

            // ADDED: Load saved potions from player account
            ArrayList<String> savedPotions = playerAccount.getPotions();
            if (savedPotions != null && !savedPotions.isEmpty()) {
                System.out.println("Loading your saved potions...");
                for (String potionName : savedPotions) {
                    Potion loadedPotion = createPotionFromName(potionName);
                    if (loadedPotion != null) {
                        player.brewedPotions.add(loadedPotion); // Direct access to ensure they're added
                        System.out.println("🧪 Restored: " + loadedPotion.getName());
                    }
                }
                System.out.println("Loaded " + savedPotions.size() + " potions from your inventory.");
            }
            
            // ADDED: Load quest data
            for (String questId : playerAccount.getActiveQuests()) {
                player.getQuestManager().restoreActiveQuest(questId);
            }
            
            for (String questId : playerAccount.getCompletedQuests()) {
                player.getQuestManager().restoreCompletedQuest(questId);
            }
            
            System.out.println("Welcome back, " + playerAccount.getUsername() + "!");
        } else {
            // Original flow for non-logged in users
            player = new Player(ConsoleUI.getPlayerName());
        }
        
        Inventory inventory = new Inventory(player.getLevel());
        this.inventory = inventory; // Initialize inventory with player level // Show available ingredients

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
                case 5 -> inventory.viewInventory(player);
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
                }
                case 7 -> handleQuestMenu(); // NEW: Quest board option
                case 8 -> {
                    playing = false;
                    exit();  // ensure session end, XP/level gain summary, logout
                }
                // Exit moved to option 8
                default -> System.out.println("Invalid choice!");
            }
        }
        
        ConsoleUI.showExit();
    }
    
// Replace this method in GameEngine.java
private Potion createPotionFromName(String name) {
    // Create potions with proper properties based on name
    switch (name) {
        case "Invisibility Potion":
            return new Potion("Invisibility Potion", 30,"Makes the drinker invisible for a short time");
        case "Fire Shield":
            return new Potion("Fire Shield", 20,"Provides protection against fire damage");
        case "Energy Blast":
            return new Potion("Energy Blast", 25,"Creates a surge of offensive energy");
        case "Speed Surge":
            return new Potion("Speed Surge", 30,"Greatly increases movement speed");
        case "Venom Vial":
            return new Potion("Venom Vial", 35,"Inflicts poison damage over time");
        case "Ocean's Grace":
            return new Potion("Ocean's Grace", 40,"Allows breathing underwater");
        case "Revive Shield":
            return new Potion("Revive Shield", 50,"Revives the user when fatally wounded");
        case "Nightmare Brew":
            return new Potion("Nightmare Brew", 55,"Causes terrifying hallucinations to enemies");
        case "Healing Elixir":
            return new Potion("Healing Elixir", 15,"Heals a small amount of health");
        // Add other potion types as needed
        default:
            System.out.println("Warning: Unknown potion type '" + name + "', creating generic version");
            return new Potion(name, 0, "No effect specified"); // Default potion with no effect
    }
}
    
    // NEW: Handle quest menu
    private void handleQuestMenu() {
        boolean questMenu = true;
        while (questMenu) {
            System.out.println("\n===== QUEST BOARD =====");
            System.out.println("1. View Available Quests");
            System.out.println("2. View Active Quests");
            System.out.println("3. View Completed Quests");
            System.out.println("4. Accept a Quest");
            System.out.println("5. Back to Main Menu");
            
            int questChoice = InputManager.readInt();
            switch (questChoice) {
                case 1 -> player.getQuestManager().displayAvailableQuests();
                case 2 -> player.getQuestManager().displayActiveQuests();
                case 3 -> player.getQuestManager().displayCompletedQuests();
                case 4 -> {
                    System.out.println("Enter quest ID to accept:");
                    String questId = InputManager.readString();
                    player.getQuestManager().acceptQuest(questId);
                    
                    // ADDED: Save quest data after accepting
                    if (playerAccount != null) {
                        try {
                            playerAccount.addActiveQuest(questId);
                            accountManager.saveAccount(playerAccount);
                        } catch (Exception e) {
                            System.err.println("Failed to save quest data: " + e.getMessage());
                        }
                    }
                }
                case 5 -> questMenu = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    private void savePlayerData() {
        try {
            // Update account with player's current stats
            playerAccount.setExperience(player.getXp());
            playerAccount.setLevel(player.getLevel());
            playerAccount.setCredits(player.Creads);
            playerAccount.setHealthPoints(player.HealthLevel);
            playerAccount.setGameLevel(player.getGameLevel());            
            playerAccount.setRank(player.getRank());
            playerAccount.setTier(player.getTier());
            playerAccount.setDefeatedMonsters(player.getDefeatedMonsters());
            playerAccount.setIngredientList(player.getIngredients());
            
            
            // MODIFIED: Clear existing potions before saving current ones
            playerAccount.clearPotions(); // Make sure to add this method to PlayerAccount
            
            // Save potions
            for (Potion potion : player.getBrewedPotions()) {
                playerAccount.addPotion(potion.getName());
            }
            // Update account with player's current stats// ADDED: Save quest data
            playerAccount.clearQuests(); // Make sure to add this method to PlayerAccount
            
            // Save active quests
            for (String questId : player.getQuestManager().getActiveQuestIds()) {
                playerAccount.addActiveQuest(questId);
            }
            
            // Save completed quests
            for (String questId : player.getQuestManager().getCompletedQuestIds()) {
                playerAccount.addCompletedQuest(questId);
            }
            
            // Save to file
            accountManager.saveAccount(playerAccount);
            
        } catch (Exception e) {
            System.err.println("Error saving player data: " + e.getMessage());
        }
    }
    
    // This method can be called from outside to save progress during the game
    public void saveProgress() {
        if (playerAccount != null) {
            try {
                // Save player data first
                savePlayerData();
                
                System.out.println("Progress saved successfully!");
            } catch (Exception e) {
                System.err.println("Error saving progress: " + e.getMessage());
            }
        } else {
            System.out.println("No account logged in. Progress cannot be saved.");
        }
    }

    public void exit() {
        if (playerAccount != null) {
            int xpGained = player.getXp() - playerAccount.getInitialXP();
            int levelsGained = player.getLevel() - playerAccount.getInitialLevel();
            try {                
                // Save player data first
                saveProgress();
                
                // Close the session properly
                accountManager.logout(playerAccount.getUsername());
                
                System.out.println("Session ended. Thanks for playing!");
                System.out.println("XP gained this session: " + xpGained);
                System.out.println("Levels gained this session: " + levelsGained);
            } catch (Exception e) {
                System.err.println("Error saving session data: " + e.getMessage());
            }
        }
    }
}