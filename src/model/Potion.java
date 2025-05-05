package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a potion that can be brewed and used in the game.
 */
public class Potion {
    private String name;
    private List<String> requiredIngredients;
    private int difficulty;

    public Potion(String name, List<String> requiredIngredients, int difficulty) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean matchesRecipe(List<String> ingredientsUsed) {
        return requiredIngredients.equals(ingredientsUsed);
    }

    @Override
    public String toString() {
        String difficultyText = difficulty > 0 ? "Difficulty: " + difficulty + ", " : "";
        String effectText = effect != null ? "Effect: " + effect : "";
        return name + " (" + difficultyText + effectText + ") - DMG: " + damage;
    }
}