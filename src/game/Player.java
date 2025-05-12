package game;

import factory.StoryFactory;
import java.io.Serializable;
import model.Monster;
import model.Potion;
import model.PlayerRank;
import utils.InputManager;

import DSA.*;

public class Player implements Serializable {
    private static final long serialVersionUID = 2L;

    private final String name;
    private int xp = 0;
    private int level = 1;
    public int Creads;
    public int GameLevel = 1;
    public int HealthLevel = 100;
    public String rank;
    public String tier;
    public int initialLevel;
    public int initialXP;
    public MapLinkedList<String, Integer> ingredients = new MapLinkedList<>();
    private final int XP_PER_LEVEL = 100;
    private int defeatedMonsters = 0;
    private QuestManager questManager;
    public ArrayList<Potion> brewedPotions = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.Creads = 0;
        this.GameLevel = 1;
        this.xp = 0;
        this.initialLevel = 1;
        this.initialXP = 0;
        this.Creads = 50;
        this.rank = "Novice";
        this.ingredients = new MapLinkedList<>();

        
        this.brewedPotions = new ArrayList<>();
        this.questManager = new QuestManager();
    }
    public Player(String name, int level, int xp, int Creads, int HealthLevel) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.Creads = Creads;
        this.HealthLevel = HealthLevel;
        this.initialLevel = level;
        this.initialXP = xp;
        this.rank = PlayerRank.getRankTitle(level);
        this.tier = PlayerRank.getTierName(level);
        this.ingredients = new MapLinkedList<>();

        
        this.brewedPotions = new ArrayList<>();
        this.questManager = new QuestManager();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
    
    public int getCreads() {
        return Creads;
    }
    
    public int getXp(){
        return xp;
    }

    public int getGameLevel() {
        return GameLevel;
    }
    
    
    public int getHealthLevel() {
        return HealthLevel;
    }
    
    public String getRank() {
        return PlayerRank.getRankTitle(getLevel());
    }
    
    public String getTier() {
        return PlayerRank.getTierName(getLevel());
    }
    
    public int getDefeatedMonsters() {
        return defeatedMonsters;
    }
    
    public void increaseDefeatedMonsters() {
        this.defeatedMonsters++;
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setDefeatedMonsters(int defeatedMonsters) {
        this.defeatedMonsters = defeatedMonsters;
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
    
    public void updateScore(int points) {
        xp += points;
        checkLevelUp();
    }

    public void checkLevelUp() {
        int newLevel = (xp / XP_PER_LEVEL) + 1;
        if (newLevel > level) {
            level = newLevel;
            System.out.println("🎉 Level Up! You reached Level " + level + "!");
            // Optional: reward system here
        }
    }

    public void viewStats() {
        System.out.println("\n===== PLAYER PROFILE =====");
        System.out.println("👤 Player: " + name);
        System.out.println("🏅 Level: " + level);
        System.out.println("💎 Rank: " + getRank());
        System.out.println("🔰 Tier: " + getTier());
        System.out.println("📊 Experience: " + xp + " / " + (level * XP_PER_LEVEL));
        System.out.println("💰 Credits: " + Creads);
        System.out.println("💘 Health: " + HealthLevel);
        System.out.println("🧪 Potions collected: " + brewedPotions.size());
        System.out.println("💀 Monsters defeated: " + defeatedMonsters);
        System.out.println("==========================");
    }

    public void GameStats() {
        System.out.println("\n👤 Player: " + name);
        System.out.println("💰 Creads: " + Creads + "\n");
        System.out.println("🚩 Current level: " + GameLevel);
        System.out.println("💘 Health: " + HealthLevel);
    }

    public void addPotion(Potion potion) {
        brewedPotions.add(potion);
        System.out.println("📦 Added to collection: " + potion.getName());
    }

    public void viewBrewedPotions() {
        if (brewedPotions.isEmpty()) {
            System.out.println("🧪 You haven't brewed any potions yet.");
        } else {
            System.out.println("\n📚 Your Brewed Potions:");
            for (Potion p : brewedPotions) {
                System.out.println("🧪 " + p);
            }
        }
    }

    public void addCreads(int amount) {
        Creads += amount;
        System.out.println("💰 You received " + amount + " Creads!");
        questManager.checkQuestProgress(this); // Check if any collection quests completed
    }

    public void addGameXP(int amount) {
        xp += amount;
        System.out.println("💎 You received " + amount + " XP!");
    }
    public void IncGameLevel( ) {
        GameLevel++;
    }
    
    public void IncHealthLevel(int amt) {
        HealthLevel += amt;
        System.out.println("💘 Your health increased to " + HealthLevel + "!");
    }
    
    public void DecHealthLevel(int amt) {
        HealthLevel -= amt;
        System.out.println("💘 Your health decreased to " + HealthLevel + "!");
    }

    public void setHealthLevel(int health) {
        this.HealthLevel = health;
        System.out.println("Health set to: " + health);
    }
    public void setCreads(int creads) {
        this.Creads = creads;
        System.out.println("Creads set to: " + creads);
    }
    
    public void setXp(int xp) {
        this.xp = xp;
        System.out.println("XP set to: " + xp);
    }
    public void setGameLevel(int gameLevel) {
        this.GameLevel = gameLevel;
        System.out.println("Game Level set to: " + gameLevel);
    }
    public void setRank(String rank) {
        this.rank = rank;
        System.out.println("Rank set to: " + rank);
    }
    public void setTier(String tier) {
        this.tier = tier;
        System.out.println("Tier set to: " + tier);
    }
    
    public void setLevel(int level) {
        this.GameLevel = level;
        System.out.println("Level set to: " + level);
    }

    public void incCreads(int amount) {
        Creads += amount;
        System.out.println("Creads increased by " + amount + ". Total: " + Creads);
    }
    public void setIngredients(ArrayList<String> ingredients) {
        MapLinkedList<String, Integer> ingredientMap = new MapLinkedList<>();
        for (String ingredient : ingredients) {
            // Increment count for each occurrence of the ingredient
            ingredientMap.put(ingredient, ingredientMap.getOrDefault(ingredient, 0) + 1);
        }
        this.ingredients = ingredientMap;
        System.out.println("Ingredients set to: " + ingredientMap);
    }
    public ArrayList<String> getIngredients() {
        ArrayList<String> ingredientList = new ArrayList<>();
        for (MapLinkedList.Pair<String, Integer> entry : ingredients.entrySet()) {
            String ingredient = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                ingredientList.add(ingredient);
            }
        }
        return ingredientList;
    }

    public void fight() {
        Monster monster = StoryFactory.generateRandomMonster(this.GameLevel);
        if (monster != null) {
            fight(monster);
        } else {
            System.out.println("No monster available for this level!");
        }
    }
    
    public void fight(Monster monster) {
        System.out.println("\n⚔️ Battle with " + monster.getName() + " begins!");
        
        while (monster.isAlive() && this.HealthLevel > 0) {
            System.out.println("\nYour Health: " + this.HealthLevel);
            System.out.println("Monster Health: " + monster.getHealth());
            
            // Show available potions
            System.out.println("\nAvailable Potions:");
            if (brewedPotions.isEmpty()) {
                System.out.println("No potions available!");
                return;
            }
            
            for (int i = 0; i < brewedPotions.size(); i++) {
                System.out.println((i + 1) + ": " + brewedPotions.get(i).getName());
            }
            
            System.out.println("\nChoose a potion to use (1-" + brewedPotions.size() + ") or 0 to flee:");
            int choice = InputManager.readInt();            
            if (choice == 0) {
                System.out.println("You fled from the battle!");
                DecHealthLevel(10); // Penalty for fleeing
                return;
            }
            
            if (choice > 0 && choice <= brewedPotions.size()) {
                Potion selectedPotion = brewedPotions.get(choice - 1);
                System.out.println("You used " + selectedPotion.getName() + "!");
                
                if (selectedPotion.getName().equals("Healing Elixir")) {
                    IncHealthLevel(selectedPotion.getIncHealth());
                } else {
                    monster.damageMonster(selectedPotion.getDamage());
                    // Monster attacks back only if still alive
                    if (monster.isAlive()) {
                        int monsterDamage = monster.attack();
                        DecHealthLevel(monsterDamage);
                    }
                }
                
                brewedPotions.remove(choice - 1);
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
        
        if (monster.isAlive()) {
            System.out.println("You were defeated! 💀");
        } else {
            System.out.println("Victory! 🎉");
            System.out.println("You defeated " + monster.getName() + "!");
            IncGameLevel(); // Increase game level
            incCreads(monster.getCredsReward()); // Reward for defeating monster
            increaseDefeatedMonsters(); // Track defeated monsters
            questManager.checkQuestProgress(this); // Check if any quests completed
        }
    }

    public void addBrewedPotion(Potion result) {
        brewedPotions.add(result);
        questManager.checkQuestProgress(this); // Check if any brewing quests completed
    }

    public ArrayList<Potion> getBrewedPotions() {
        return brewedPotions;
    }
}