package model;

import java.util.List;

public class Potion {
    private String name;
    private List<String> requiredIngredients;
    private int difficulty;
    private int Damage;
    

    public Potion(String name, List<String> requiredIngredients, int difficulty,int Damage) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.difficulty = difficulty;
        this.Damage=Damage;
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
    public int getDamage() {
        return Damage;
    }

    @Override
    public String toString() {
        return name + " (Difficulty: " + difficulty + ")";
    }
}
