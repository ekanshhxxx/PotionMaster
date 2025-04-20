package model;

import java.util.List;

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
        return name + " (Difficulty: " + difficulty + ")";
    }
}
