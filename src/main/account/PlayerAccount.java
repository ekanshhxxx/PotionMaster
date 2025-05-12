package main.account;

import game.Player;
import java.io.Serializable;
import java.lang.reflect.Array;

import DSA.*;

public class PlayerAccount implements Serializable {
    private static final long serialVersionUID = 2L;

    private Player player;
    private String username;
    private String passwordHash; // Store hashed password, never plain text
    // private Map<String, Integer> ingredients;
    private int initialLevel;
    private int initialXP;
    private long lastLoginTime;
    private long totalPlayTime;
    private ArrayList<String> savedPotions = new ArrayList<>();
    private ArrayList<String> activeQuests = new ArrayList<>();
private ArrayList<String> completedQuests = new ArrayList<>();

    
    public PlayerAccount(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.player= new Player(username);
        this.savedPotions = new ArrayList<>();
        this.lastLoginTime = System.currentTimeMillis();
        this.totalPlayTime = 0;
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
        long totalPlayTime = System.currentTimeMillis() - lastLoginTime;
        System.out.println("Total play time: " + totalPlayTime / (1000 * 60) + " minutes");
        return totalPlayTime / (1000 * 60); // Convert milliseconds to minutes
    }
    public ArrayList<String> getActiveQuests() {
        return activeQuests;
    }
    
    public ArrayList<String> getCompletedQuests() {
        return completedQuests;
    }

    public int getCredits() {
        return player.getCreads();
    }

    public int getXp() {
        int xp = player.getXp();
        return xp;
    }

    public int getGameLevel() {
        return player.getGameLevel();
    }
    public int  getHealthPoints() {
        return player.getHealthLevel();
    }
    public int getLevel(){
        return player.getLevel();
    }

    public void setLevel(int level) {
        this.player.setLevel(level);
    }
    public void setExperience(int xp) {
        this.player.setXp(xp);
    }
    public void setGameLevel(int gameLevel) {
        this.player.setGameLevel(gameLevel);
    }
    public void setHealthPoints(int healthPoints) {
        this.player.setHealthLevel(healthPoints);
    }
    public void setCredits(int credits) {
        this.player.setCreads(credits);
    }
    public void setTotalPlayTime(long totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }
    public void setRank(String rank) {
        this.player.setRank(rank);
    }
    public String getRank() {
        return player.getRank();
    }
    public void setTier(String tier) {
        this.player.setTier(tier);
    }
    public String getTier() {
        return player.getTier();
    }
    public void setDefeatedMonsters(int defeatedMonsters) {
        this.player.setDefeatedMonsters(defeatedMonsters);
    }
    public void setIngredientList(ArrayList<String> ingredients) {
        this.player.setIngredients(ingredients);
    }
    public ArrayList<String> getIngredientList() {
        return player.getIngredients();
    }
    public void setInitialXP(int initialXP) {
        this.initialXP = initialXP;
    }
    public void setInitialLevel(int initialLevel) {
        this.initialLevel = initialLevel;
    }
    public void setInitialGameLevel(int initialGameLevel) {
        this.player.setGameLevel(initialGameLevel);
    }

    public void saveInitialStats() {
        this.initialLevel = player.getLevel();
        this.initialXP = player.getXp();
    }

    public int getInitialLevel() {
        return initialLevel;
    }
    public int getInitialXP() {
        return initialXP;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ArrayList<String> getPotions() {
        return savedPotions;
    }
    public void addPotion(String potion) {
        this.savedPotions.add(potion);
    }
    
    public void removePotion(String potion) {
        this.savedPotions.remove(potion);
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
public void clearPotions() {
    if (this.savedPotions != null) {
        this.savedPotions.clear();
    } else {
        this.savedPotions = new ArrayList<>();
    }
}
    
    @Override
    public String toString() {
        return "PlayerAccount{" +
               "username='" + username + '\'' +
               ", level=" + player.getLevel() +
               ", rank='" + player.getRank() + '\'' +
               ", gameLevel=" + player.getGameLevel() +
               ", tier='" + player.getTier() + '\'' +
               ", experience=" + player.getXp() +
               ", health=" + player.getHealthLevel() +
               ", credits=" + player.getCreads() +
               ", potions=" + savedPotions.size() +
               ", ingredients=" + player.ingredients +
               ", totalPlayTime=" + getTotalPlayTimeMinutes() + " minutes" +
               '}';
    }
}