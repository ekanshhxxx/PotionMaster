package model;

import game.Player;
import java.util.function.Predicate;

public class Quest {
    private String id;
    private String name;
    private String description;
    private Predicate<Player> completionCondition;
    private int xpReward;
    private int creadReward;
    private Potion potionReward;
    private boolean completed;
    private QuestType type;
    
    public enum QuestType {
        BREWING("Brewing"),
        COMBAT("Combat"),
        EXPLORATION("Exploration"),
        COLLECTION("Collection");
        
        private final String displayName;
        
        QuestType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public Quest(String id, String name, String description, QuestType type, 
                 Predicate<Player> completionCondition, int xpReward, int creadReward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.completionCondition = completionCondition;
        this.xpReward = xpReward;
        this.creadReward = creadReward;
        this.completed = false;
    }
    
    public Quest withPotionReward(Potion potion) {
        this.potionReward = potion;
        return this;
    }
    
    public boolean checkCompletion(Player player) {
        if (!completed && completionCondition.test(player)) {
            completed = true;
            return true;
        }
        return false;
    }
    // Add this method
public void markAsCompleted() {
    this.completed = true;
}
    
public void giveReward(Player player) {
    if (completed) {
        player.addGameXP(xpReward);
        player.addCreads(creadReward);
        
        if (potionReward != null) {
            player.addBrewedPotion(potionReward);
            System.out.println("🎁 Quest reward: " + potionReward.getName() + " potion");
        }
        
        System.out.println("✅ Quest completed: " + name);
    }
}
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public QuestType getType() { return type; }
public String getRewardText() {
    StringBuilder rewards = new StringBuilder();
    rewards.append("💎 XP: ").append(xpReward).append(", 💰 Credits: ").append(creadReward);
    if (potionReward != null) {
        rewards.append(", 🧪 ").append(potionReward.getName());
    }
    return rewards.toString();
}
}