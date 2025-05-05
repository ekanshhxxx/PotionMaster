package main.account;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// Remove these imports since they're in the same package
// import main.account.UserSessionTracker;
// import main.account.UserSessionTracker.UserSession;

public class AccountManager {
    private static final String DATA_DIRECTORY = "playerdata";
    private Map<String, PlayerAccount> loggedInAccounts;
    private Map<String, UserSessionTracker.UserSession> activeSessions;
    
    public AccountManager() {
        loggedInAccounts = new HashMap<>();
        activeSessions = new HashMap<>();
        
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
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write account data as plain text
            writer.println("username=" + account.getUsername());
            writer.println("password=" + account.getPassword());
            writer.println("level=" + account.getLevel());
            writer.println("experience=" + account.getExperience());
            writer.println("credits=" + account.getCredits());
            writer.println("health=" + account.getHealthPoints());
            
            // Write brewed potions if any
            List<String> potions = account.getPotions();
            if (!potions.isEmpty()) {
                writer.println("potions=" + String.join(",", potions));
            }
            
            // Write active quests
            List<String> activeQuests = account.getActiveQuests();
            if (!activeQuests.isEmpty()) {
                writer.println("activeQuests=" + String.join(",", activeQuests));
            }
            
            // Write completed quests
            List<String> completedQuests = account.getCompletedQuests();
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
            int experience = 0;
            int credits = 0;
            int health = 100;
            List<String> potions = new ArrayList<>();
            List<String> activeQuests = new ArrayList<>();    // Add this line
            List<String> completedQuests = new ArrayList<>(); // Add this line
            
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
                    case "potions":
                        if (!value.isEmpty()) {
                            potions = Arrays.asList(value.split(","));
                        }
                        break;
                    // Add these new cases:
                    case "activeQuests":
                        if (!value.isEmpty()) {
                            activeQuests = Arrays.asList(value.split(","));
                        }
                        break;
                    case "completedQuests":
                        if (!value.isEmpty()) {
                            completedQuests = Arrays.asList(value.split(","));
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
                int xpGained = account.getExperience() - initialXP;
                int levelsGained = account.getLevel() - initialLevel;
                
                // Track logout
                UserSessionTracker.closeSession(username, xpGained, levelsGained);
                
                saveAccount(account); // Save account data on logout
                loggedInAccounts.remove(username);
                activeSessions.remove(username);
                
                System.out.println("Session ended. You gained " + xpGained + " XP and " + 
                                   levelsGained + " levels this session.");
            }
        } catch (IOException e) {
            System.err.println("Error during logout: " + e.getMessage());
        }
    }
    
    public boolean isLoggedIn(String username) {
        return loggedInAccounts.containsKey(username);
    }
    
    public Map<String, PlayerAccount> getLoggedInAccounts() {
        return loggedInAccounts;
    }
}