package model;

import DSA.*;
/**
 * Represents a potion that can be brewed and used in the game.
 */
public class Potion {
    private String name;
    private ArrayList<String> requiredIngredients;
    private int difficulty;
    private int damage;
    private String effect; // Effect of the potion, e.g., "Heals 20 HP", "Increases attack by 5"
    private int incHealth; // Health increase for healing potions

    public Potion(String name, ArrayList<String> requiredIngredients, int difficulty,int damage, String effect) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.difficulty = difficulty;
        this.damage = damage;
        this.effect = effect;
        this.incHealth = 0; // Default to 0 for non-healing potions
    }
    public Potion(String name, ArrayList<String> requiredIngredients, int difficulty,int damage,int incHealth, String effect) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.difficulty = difficulty;
        this.incHealth = incHealth;
        this.damage = damage;
        this.effect = effect;
    }
    
    public Potion(String name,int damage, String effect) {
        this.name = name;
        this.requiredIngredients = null; // No ingredients required for this potion
        this.difficulty = 0; // No difficulty for this potion
        this.damage = damage;
        this.effect = effect;
        this.incHealth = 0; // Default to 0 for non-healing potions
    }
    // public Potion(String name){
    //     this.name = name; // Default to 0 for non-healing potions
    // }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public int getDamage() {
        return damage;
    }
    public String getEffect() {
        return effect;
    }
    public int getIncHealth() {
        return incHealth;
    }
    public boolean matchesRecipe(ArrayList<String> ingredientsUsed) {
        return requiredIngredients.equals(ingredientsUsed);
    }

    @Override
    public String toString() {
        String difficultyText = difficulty > 0 ? "Difficulty: " + difficulty + ", " : "";
        String effectText = effect != null ? "Effect: " + effect : "";
        return name + " (" + difficultyText + effectText + ") - DMG: " + damage;
    }
}