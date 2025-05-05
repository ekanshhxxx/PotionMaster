package main.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAccount implements Serializable {
    private static final long serialVersionUID = 2L;
    
    private String username;
    private String passwordHash; // Store hashed password, never plain text
    private int level;
    private int experience;
    private int healthPoints;
    private int credits;
    private String rank;
    private int initialLevel;
    private int initialXP;
    private List<String> potions;
    private Map<String, Integer> ingredients;
    private long lastLoginTime;
    private long totalPlayTime;
    private List<String> activeQuests = new ArrayList<>();
private List<String> completedQuests = new ArrayList<>();

    
    public PlayerAccount(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.level = 1;
        this.experience = 0;
        this.healthPoints = 100;
        this.credits = 50;
        this.rank = "Novice";
        this.potions = new ArrayList<>();
        this.ingredients = new HashMap<>();
        this.lastLoginTime = System.currentTimeMillis();
        this.totalPlayTime = 0;
    }

    public void saveInitialStats() {
        this.initialLevel = this.level;
        this.initialXP = this.experience;
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return passwordHash; // This returns the hash, not plain text
    }
    
    public boolean validatePassword(String hashedPassword) {
        return this.passwordHash.equals(hashedPassword);
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
        updateRank(); // Update rank when level changes
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setExperience(int experience) {
        this.experience = experience;
        
        // Check if player can level up
        int requiredXP = level * 100; // Simple progression formula
        if (experience >= requiredXP) {
            setLevel(level + 1);
            this.experience -= requiredXP;
        }
    }

    public int getInitialLevel() {
        return initialLevel;
    }
    
    public int getInitialXP() {
        return initialXP;
    }
    
    public int getHealthPoints() {
        return healthPoints;
    }
    
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public String getRank() {
        return rank;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    public String getTier() {
        if (level >= 46) return "Divine Tier";
        if (level >= 41) return "Mythic Tier";
        if (level >= 36) return "Legendary Tier";
        if (level >= 31) return "Mastery Tier";
        if (level >= 26) return "Elite Tier";
        if (level >= 21) return "Specialist Tier";
        if (level >= 16) return "Adept Tier";
        if (level >= 11) return "Journeyman Tier";
        if (level >= 6) return "Apprentice Tier";
        return "Initiation Tier";
    }
    
    private void updateRank() {
        if (level >= 46) rank = "Grandmaster Alchemage";
        else if (level >= 41) rank = "Potion Sage";
        else if (level >= 36) rank = "Elixir Warden";
        else if (level >= 31) rank = "Master Brewer";
        else if (level >= 26) rank = "Arcane Alchemist";
        else if (level >= 21) rank = "Infusion Adept";
        else if (level >= 16) rank = "Battle Chemist";
        else if (level >= 11) rank = "Combat Mixer";
        else if (level >= 6) rank = "Apprentice Brewer";
        else rank = "Novice Alchemist";
    }
    
    public List<String> getPotions() {
        return potions;
    }
    
    public void addPotion(String potion) {
        this.potions.add(potion);
    }
    
    public void removePotion(String potion) {
        this.potions.remove(potion);
    }
    
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
    
    public void addIngredient(String ingredient, int quantity) {
        this.ingredients.put(ingredient, this.ingredients.getOrDefault(ingredient, 0) + quantity);
    }
    
    public void removeIngredient(String ingredient, int quantity) {
        int currentAmount = this.ingredients.getOrDefault(ingredient, 0);
        int newAmount = Math.max(0, currentAmount - quantity);
        
        if (newAmount > 0) {
            this.ingredients.put(ingredient, newAmount);
        } else {
            this.ingredients.remove(ingredient);
        }
    }
    
    public long getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void updateLoginTime() {
        this.lastLoginTime = System.currentTimeMillis();
    }
    
    public void updatePlayTime(long sessionTimeMs) {
        this.totalPlayTime += sessionTimeMs;
    }
    
    public long getTotalPlayTimeMinutes() {
        return totalPlayTime / (1000 * 60);
    }

    public List<String> getActiveQuests() {
        return activeQuests;
    }
    
    public List<String> getCompletedQuests() {
        return completedQuests;
    }
    
    public void addActiveQuest(String questId) {
        if (!activeQuests.contains(questId)) {
            activeQuests.add(questId);
        }
    }
    
    public void removeActiveQuest(String questId) {
        activeQuests.remove(questId);
    }
    
    
    public void addCompletedQuest(String questId) {
        if (!completedQuests.contains(questId)) {
            completedQuests.add(questId);
        }
    }

    public void clearQuests() {
    this.activeQuests.clear();
    this.completedQuests.clear();
}

    // Add this method to PlayerAccount class
public void clearPotions() {
    if (this.potions != null) {
        this.potions.clear();
    } else {
        this.potions = new ArrayList<>();
    }
}
    
    @Override
    public String toString() {
        return "PlayerAccount{" +
               "username='" + username + '\'' +
               ", level=" + level +
               ", rank='" + rank + '\'' +
               ", tier='" + getTier() + '\'' +
               ", experience=" + experience +
               ", health=" + healthPoints +
               ", credits=" + credits +
               ", potions=" + potions.size() +
               ", ingredients=" + ingredients.size() +
               ", totalPlayTime=" + getTotalPlayTimeMinutes() + " minutes" +
               '}';
    }
}