package game;

import model.Potion;

import java.util.*;


public class Player {
    private final String name;
    private int score = 0;
    private int xp = 0;
    private int level = 1;
    private final int XP_PER_LEVEL = 50;



    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.level = 1;
        this.brewedPotions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void updateScore(int points) {
        score += points;
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
        System.out.println("\n👤 Player: " + name);
        System.out.println("⭐ Score: " + score + "\n");
        System.out.println("📊 XP: " + xp);
        System.out.println("🏅 Level: " + level);

    }
    private List<Potion> brewedPotions = new ArrayList<>();

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

    public void addBrewedPotion(Potion result) {
        brewedPotions.add(result); // ✅ use the parameter, not the field
    }


    public List<Potion> getBrewedPotions() {
        return brewedPotions;
    }
}
