package game;

import factory.StoryFactory;
import model.Monster;
import model.Potion;
<<<<<<< HEAD
import model.PlayerRank;
import utils.InputManager;
=======
import model.Monster;
import factory.StoryFactory;
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942

import java.util.*;

public class Player {
    private final String name;
    private int xp = 0;
    private int level = 1;
    public int Creads;
    public int GameLevel = 1;
    public int GameXP = 0;
    public int HealthLevel = 100;
    private final int XP_PER_LEVEL = 50;
    private int defeatedMonsters = 0;
    private QuestManager questManager;
    public List<Potion> brewedPotions = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.Creads = 0;
        this.GameLevel = 1;
        this.GameXP = 0;
        this.brewedPotions = new ArrayList<>();
        this.questManager = new QuestManager();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
<<<<<<< HEAD
    
    public int getCreads() {
        return Creads;
    }
    
    public int getGameLevel() {
        return GameLevel;
    }
    
    public int getGameXP() {
        return GameXP;
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
=======
    public int getCreads() {
        return Creads;
    }
    public int getGameLevel() {
        return GameLevel;
    }
    public int getGameXP() {
        return GameXP;
    }
    public int getHealthLevel() {
        return HealthLevel;
    }

>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
    
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
<<<<<<< HEAD
        System.out.println("\n===== PLAYER PROFILE =====");
        System.out.println("👤 Player: " + name);
=======
        System.out.println("\n👤 Player: " + name);
        System.out.println("📊 XP: " + xp);
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
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
<<<<<<< HEAD
=======
    public void GameStats() {
        System.out.println("\n👤 Player: " + name);
        System.out.println("💰 Creads: " + Creads + "\n");
        System.out.println("🚩 Current level: " + GameLevel);
        System.out.println("💘 Health: " + HealthLevel);

    }
    public List<Potion> brewedPotions = new ArrayList<>();
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942

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
<<<<<<< HEAD
        questManager.checkQuestProgress(this); // Check if any collection quests completed
=======
    }

    public void addGameXP(int amount) {
        GameXP += amount;
        System.out.println("💎 You received " + amount + " XP!");
    }
    public void IncGameLevel( ) {
        GameLevel++;
        System.out.println("🚩 You leveled up to level " + GameLevel + "!");
    }
    public void IncHealthLevel(int amt ) {
        HealthLevel+= amt;
        System.out.println("💘 You leveled up to level " + HealthLevel + "!");
    }
    public void DecHealthLevel(int amt ) {
        HealthLevel-= amt;
        System.out.println("💘 You leveled down to level " + HealthLevel + "!");
    }

    public void fight(){
        Scanner scanner = new Scanner(System.in);
        Monster monster = StoryFactory.generateRandomMonster(this.GameLevel);
        if (monster != null) {
            fight(monster, scanner);
        } else {
            System.out.println("No monster available for this level!");
        }
    }
    
    public void fight(Monster monster, Scanner scanner) {
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
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                System.out.println("You fled from the battle!");
                DecHealthLevel(10); // Penalty for fleeing
                return;
            }
            
            if (choice > 0 && choice <= brewedPotions.size()) {
                Potion selectedPotion = brewedPotions.get(choice - 1);
                monster.damageMonster(selectedPotion.getDamage());
                System.out.println("You used " + selectedPotion.getName() + "!");
                brewedPotions.remove(choice - 1);
                
                // Monster attacks back
                int monsterDamage = monster.attack();
                DecHealthLevel(monsterDamage);
            }
        }
        
        if (monster.isAlive()) {
            System.out.println("You were defeated! 💀");
        } else {
            System.out.println("Victory! 🎉");
            addGameXP(20);
            addCreads(50);
        }
    }

    public void addBrewedPotion(Potion result) {
        brewedPotions.add(result); // ✅ use the parameter, not the field
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
    }

    public void addGameXP(int amount) {
        GameXP += amount;
        System.out.println("💎 You received " + amount + " XP!");
    }
    
    public void IncGameLevel() {
        GameLevel++;
        System.out.println("🚩 You leveled up to level " + GameLevel + "!");
        questManager.checkQuestProgress(this); // Check if any level quests completed
    }
    
    public void IncHealthLevel(int amt) {
        HealthLevel += amt;
        System.out.println("💘 Your health increased to " + HealthLevel + "!");
    }
    
    public void DecHealthLevel(int amt) {
        HealthLevel -= amt;
        System.out.println("💘 Your health decreased to " + HealthLevel + "!");
    }
    
    public void setLevel(int level) {
        this.GameLevel = level;
        System.out.println("Level set to: " + level);
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
                monster.damageMonster(selectedPotion.getDamage());
                System.out.println("You used " + selectedPotion.getName() + "!");
                brewedPotions.remove(choice - 1);
                
                // Monster attacks back
                int monsterDamage = monster.attack();
                DecHealthLevel(monsterDamage);
            }
        }
        
        if (monster.isAlive()) {
            System.out.println("You were defeated! 💀");
        } else {
            System.out.println("Victory! 🎉");
            addGameXP(20);
            addCreads(50);
            increaseDefeatedMonsters(); // Track defeated monsters
            questManager.checkQuestProgress(this); // Check if any quests completed
        }
    }

    public void addBrewedPotion(Potion result) {
        brewedPotions.add(result);
        questManager.checkQuestProgress(this); // Check if any brewing quests completed
    }

    public List<Potion> getBrewedPotions() {
        return brewedPotions;
    }
}