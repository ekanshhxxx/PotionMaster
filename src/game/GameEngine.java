package game;

import model.*;
import factory.RecipeFactory;
import utils.ConsoleUI;
import main.account.PlayerAccount;
import utils.InputManager;

import java.util.List;

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
            player.updateScore(playerAccount.getExperience());
            player.setLevel(playerAccount.getLevel());
            player.Creads = playerAccount.getCredits();
            player.HealthLevel = playerAccount.getHealthPoints();
            
            // ADDED: Load saved potions from player account
            List<String> savedPotions = playerAccount.getPotions();
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
<<<<<<< HEAD
                }
                case 7 -> handleQuestMenu(); // NEW: Quest board option
                case 8 -> playing = false;   // Exit moved to option 8
=======
                    ConsoleUI.showMenu(); // Show main menu after exiting game menu
                }
                case 7 -> playing = false;

>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
                default -> System.out.println("Invalid choice!");
            }
        }

        // Save player data before exiting if logged in
        if (playerAccount != null) {
            try {
                savePlayerData();
                System.out.println("Your progress has been saved.");
            } catch (Exception e) {
                System.err.println("Error saving progress: " + e.getMessage());
            }
        }
        
        ConsoleUI.showExit();
    }
    
// Replace this method in GameEngine.java
private Potion createPotionFromName(String name) {
    // Create potions with proper properties based on name
    switch (name) {
        case "Fire Shield":
            return new Potion("Fire Shield", 30);
        case "Ice Blast":
            return new Potion("Ice Blast", 25);
        case "Health Potion":
            return new Potion("Health Potion", 20);
        case "Thunder Strike":
            return new Potion("Thunder Strike", 35);
        case "Earth Armor":
            return new Potion("Earth Armor", 30);
        case "Venom Splash":
            return new Potion("Venom Splash", 28);
        // Add other potion types as needed
        default:
            System.out.println("Warning: Unknown potion type '" + name + "', creating generic version");
            return new Potion(name, 15);
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
            playerAccount.setExperience(player.GameXP);
            playerAccount.setLevel(player.getLevel());
            playerAccount.setCredits(player.Creads);
            playerAccount.setHealthPoints(player.HealthLevel);
            
            // MODIFIED: Clear existing potions before saving current ones
            playerAccount.clearPotions(); // Make sure to add this method to PlayerAccount
            
            // Save potions
            for (Potion potion : player.getBrewedPotions()) {
                playerAccount.addPotion(potion.getName());
            }
            
            // ADDED: Save quest data
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
                savePlayerData();
                System.out.println("Game progress saved successfully.");
            } catch (Exception e) {
                System.err.println("Failed to save game progress: " + e.getMessage());
            }
        } else {
            System.out.println("You need to be logged in to save your progress.");
        }
    }

    public void exit() {
        if (playerAccount != null) {
            try {
                // Calculate stats gained during this session
                int xpGained = player.GameXP - playerAccount.getInitialXP();
                int levelsGained = player.getLevel() - playerAccount.getInitialLevel();
                
                // Save player data first
                savePlayerData();
                
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