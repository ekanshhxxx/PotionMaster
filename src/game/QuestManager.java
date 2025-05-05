package game;

import model.Quest;
// import model.Potion;
import java.util.*;

public class QuestManager {
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;
    private Map<String, Quest> availableQuests;
    
    public QuestManager() {
        activeQuests = new ArrayList<>();
        completedQuests = new ArrayList<>();
        availableQuests = new HashMap<>();
        initializeQuests();
    }
    
    private void initializeQuests() {
        // Combat quests
        availableQuests.put("combat1", new Quest("combat1", "Monster Hunter", 
            "Defeat 3 monsters", Quest.QuestType.COMBAT,
            player -> player.getDefeatedMonsters() >= 3, 30, 100));
            
        // Brewing quests
        availableQuests.put("brew1", new Quest("brew1", "Apprentice Brewer", 
            "Brew your first potion", Quest.QuestType.BREWING,
            player -> player.getBrewedPotions().size() >= 1, 10, 50));
            
        availableQuests.put("brew2", new Quest("brew2", "Master Brewer", 
            "Brew 5 different potions", Quest.QuestType.BREWING,
            player -> player.getBrewedPotions().size() >= 5, 50, 150));
            
        // Collection quests
        availableQuests.put("collect1", new Quest("collect1", "Collector", 
            "Earn 500 credits", Quest.QuestType.COLLECTION,
            player -> player.getCreads() >= 500, 40, 100));
            
        // Level-up quests
        availableQuests.put("level1", new Quest("level1", "Leveling Up", 
            "Reach game level 3", Quest.QuestType.EXPLORATION,
            player -> player.getGameLevel() >= 3, 50, 200));
    }
    
    public void acceptQuest(String questId) {
        Quest quest = availableQuests.get(questId);
        if (quest != null && !activeQuests.contains(quest) && !completedQuests.contains(quest)) {
            activeQuests.add(quest);
            System.out.println("📜 Quest accepted: " + quest.getName());
        } else {
            System.out.println("❌ Quest not found or already accepted/completed.");
        }
    }
    
    public void checkQuestProgress(Player player) {
        Iterator<Quest> iterator = activeQuests.iterator();
        while (iterator.hasNext()) {
            Quest quest = iterator.next();
            if (quest.checkCompletion(player)) {
                quest.giveReward(player);
                completedQuests.add(quest);
                iterator.remove();
            }
        }
    }
    
    public void displayAvailableQuests() {
        System.out.println("\n===== AVAILABLE QUESTS =====");
        if (availableQuests.isEmpty()) {
            System.out.println("No quests available at the moment.");
        } else {
            for (Quest quest : availableQuests.values()) {
                if (!activeQuests.contains(quest) && !completedQuests.contains(quest)) {
                    displayQuest(quest);
                }
            }
        }
    }
    
    public void displayActiveQuests() {
        System.out.println("\n===== ACTIVE QUESTS =====");
        if (activeQuests.isEmpty()) {
            System.out.println("No active quests. Accept some from available quests!");
        } else {
            for (Quest quest : activeQuests) {
                displayQuest(quest);
            }
        }
    }
    
    public void displayCompletedQuests() {
        System.out.println("\n===== COMPLETED QUESTS =====");
        if (completedQuests.isEmpty()) {
            System.out.println("You haven't completed any quests yet.");
        } else {
            for (Quest quest : completedQuests) {
                System.out.println("✅ " + quest.getName() + " - " + quest.getDescription());
            }
        }
    }
    
    private void displayQuest(Quest quest) {
        System.out.println("\n📜 " + quest.getName() + " [" + quest.getType().getDisplayName() + "]");
        System.out.println("   " + quest.getDescription());
        System.out.println("   Rewards: " + quest.getRewardText());
        System.out.println("   ID: " + quest.getId());
    }
    
    public List<String> getActiveQuestIds() {
        List<String> ids = new ArrayList<>();
        for (Quest quest : activeQuests) {
            ids.add(quest.getId());
        }
        return ids;
    }

    public List<String> getCompletedQuestIds() {
        List<String> ids = new ArrayList<>();
        for (Quest quest : completedQuests) {
            ids.add(quest.getId());
        }
        return ids;
    }

    public void restoreActiveQuest(String questId) {
        Quest quest = availableQuests.get(questId);
        if (quest != null && !activeQuests.contains(quest) && !completedQuests.contains(quest)) {
            activeQuests.add(quest);
        }
    }

    public void restoreCompletedQuest(String questId) {
        Quest quest = availableQuests.get(questId);
        if (quest != null && !completedQuests.contains(quest)) {
            quest.markAsCompleted(); // Make sure to add this method to Quest class
            completedQuests.add(quest);
            activeQuests.remove(quest); // Remove from active if present
        }
    }
    
    public Quest getQuestById(String id) {
        return availableQuests.get(id);
    }
}