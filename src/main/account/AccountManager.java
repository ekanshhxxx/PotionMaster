package main.account;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import DSA.*;


public class AccountManager {
    private static final String DATA_DIRECTORY = "playerdata";
    private MapLinkedList<String, PlayerAccount> loggedInAccounts;
    private MapLinkedList<String, UserSessionTracker.UserSession> activeSessions;
    
    public AccountManager() {
        loggedInAccounts = new MapLinkedList<>();
        activeSessions = new MapLinkedList<>();
        
        // Create data directory if it doesn't exist
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    public PlayerAccount login(String username, String password) {
        // Check if account exists
        File accountFile = new File(DATA_DIRECTORY + File.separator + username + ".txt");
        if (!accountFile.exists()) {
            return null; // Account doesn't exist
        }
        
        try {
            // Read account data
            PlayerAccount account = loadAccount(username);
            
            // Validate password
            if (account.validatePassword(hashPassword(password))) {
                loggedInAccounts.put(username, account);
                
                // Save initial stats for session tracking
                account.saveInitialStats();
                
                // Track login session
                try {
                    UserSessionTracker.UserSession session = UserSessionTracker.createSession(username);
                    activeSessions.put(username, session);
                } catch (IOException e) {
                    System.err.println("Error recording login: " + e.getMessage());
                }
                
                return account;
            }
            
        } catch (IOException e) {
            // Removed ClassNotFoundException since it's not thrown anymore
            System.err.println("Error loading account: " + e.getMessage());
        }
        
        return null; // Login failed
    }
    
    public boolean registerAccount(String username, String password) {
        // Check if account already exists
        File accountFile = new File(DATA_DIRECTORY + File.separator + username + ".dat");
        if (accountFile.exists()) {
            return false; // Account already exists
        }
        
        // Create new account
        PlayerAccount newAccount = new PlayerAccount(username, hashPassword(password));
        
        try {
            // Save account
            saveAccount(newAccount);
            return true;
        } catch (IOException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }
    
    public void saveAccount(PlayerAccount account) throws IOException {
        String filename = DATA_DIRECTORY + File.separator + account.getUsername() + ".txt";
        System.out.println(account.getUsername());
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write account data as plain text
            writer.println("username=" + account.getUsername());
            writer.println("password=" + account.getPassword());
            writer.println("level=" + account.getLevel());
            writer.println("GameLevel="+ account.getGameLevel());
            writer.println("rank=" + account.getRank());
            writer.println("tier=" + account.getTier());
            writer.println("experience=" + account.getXp());
            writer.println("credits=" + account.getCredits());
            writer.println("health=" + account.getHealthPoints());
            
            // Write brewed potions if any
            ArrayList<String> potions = account.getPotions();
            if (!potions.isEmpty()) {
                writer.println("potions=" + String.join(",", potions));
            }
            
            // Write active quests
            ArrayList<String> activeQuests = account.getActiveQuests();
            if (!activeQuests.isEmpty()) {
                writer.println("activeQuests=" + String.join(",", activeQuests));
            }
            
            // Write completed quests
            ArrayList<String> completedQuests = account.getCompletedQuests();
            if (!completedQuests.isEmpty()) {
                writer.println("completedQuests=" + String.join(",", completedQuests));
            }
        }
    }
    
    public PlayerAccount loadAccount(String username) throws IOException {
        String filename = DATA_DIRECTORY + File.separator + username + ".txt";
        PlayerAccount account = null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String storedPassword = null;
            int level = 1;
            int Gamelevel = 1;
            String rank = null;
            String tier = null;
            int defeatedMonsters =0;
            int experience = 0;
            int credits = 0;
            int health = 100;
            ArrayList<String> ingedientList = new ArrayList<>();
            ArrayList<String> potions = new ArrayList<>();
            ArrayList<String> activeQuests = new ArrayList<>();    // Add this line
            ArrayList<String> completedQuests = new ArrayList<>(); // Add this line
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                
                String key = parts[0];
                String value = parts[1];
                
                switch (key) {
                    case "password":
                        storedPassword = value;
                        break;
                    case "level":
                        level = Integer.parseInt(value);
                        break;
                    case "experience":
                        experience = Integer.parseInt(value);
                        break;
                    case "credits":
                        credits = Integer.parseInt(value);
                        break;
                    case "health":
                        health = Integer.parseInt(value);
                        break;
                    case "rank":
                        rank = value;
                        break;
                    case "tier":
                        tier = value;
                        break;
                    case "defeatedMonsters":
                        defeatedMonsters = Integer.parseInt(value);
                        break;
                    // Read potions if any
                    case "GameLevel":
                        Gamelevel = Integer.parseInt(value);
                        break;

                    case "ingedientList":
                        if (!value.isEmpty()) {
                            String[] ingredients = value.split(",");
                            ingedientList = new ArrayList<>();
                            for (String ingredient : ingredients) {
                                ingedientList.add(ingredient);
                            }
                        }
                        break;
                    case "potions":
                        if (!value.isEmpty()) {
                            String[] potionArray = value.split(",");
                            potions = new ArrayList<>();
                            for (String potion : potionArray) {
                                potions.add(potion);
                            }
                        }
                        break;
                    // Add these new cases:
                    case "activeQuests":
                        if (!value.isEmpty()) {
                            String[] questArray = value.split(",");
                            activeQuests = new ArrayList<>();
                            for (String quest : questArray) {
                                activeQuests.add(quest);
                            }
                        }
                        break;
                    case "completedQuests":
                        if (!value.isEmpty()) {
                            String[] questArray = value.split(",");
                            completedQuests = new ArrayList<>();
                            for (String quest : questArray) {
                                completedQuests.add(quest);
                            }
                        }
                        break;
                }
            }
            
            // Create account with loaded data
            account = new PlayerAccount(username, storedPassword);
            account.setLevel(level);
            account.setExperience(experience);
            account.setCredits(credits);
            account.setHealthPoints(health);    
            account.setGameLevel(Gamelevel);
            account.setRank(rank);
            account.setTier(tier);
            account.setDefeatedMonsters(defeatedMonsters);
            account.setIngredientList(ingedientList);
            account.setInitialXP(experience);
            account.setInitialLevel(level);
            account.setInitialGameLevel(Gamelevel);
            
            for (String potion : potions) {
                account.addPotion(potion);
            }
            
            // Add the quest data
            for (String questId : activeQuests) {
                account.addActiveQuest(questId);
            }
            
            for (String questId : completedQuests) {
                account.addCompletedQuest(questId);
            }
        }
        
        return account;
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public void logout(String username) {
        try {
            PlayerAccount account = loggedInAccounts.get(username);
            if (account != null) {
                // Calculate stats for session tracking
                int initialXP = account.getInitialXP();
                int initialLevel = account.getInitialLevel();
                int xpGained = account.getXp() - initialXP;
                int levelsGained = account.getLevel() - initialLevel;
                
                // Track logout
                UserSessionTracker.closeSession(username, xpGained, levelsGained);
                
                saveAccount(account); // Save account data on logout
                loggedInAccounts.remove(username);
                activeSessions.remove(username);
    
            }
        } catch (IOException e) {
            System.err.println("Error during logout: " + e.getMessage());
        }
    }
    
    public boolean isLoggedIn(String username) {
        return loggedInAccounts.containsKey(username);
    }
    
    public MapLinkedList<String, PlayerAccount> getLoggedInAccounts() {
        return loggedInAccounts;
    }
}