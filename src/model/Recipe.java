package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe {
    private List<String> ingredients;
    private Potion potion;
    private int requiredLevel;
    
    public Recipe(List<String> ingredients, Potion potion, int requiredLevel) {
        this.ingredients = ingredients;
        this.potion = potion;
        this.requiredLevel = requiredLevel;
    }
    
    public List<String> getIngredients() {
        return ingredients;
    }
    
    public Potion getPotion() {
        return potion;
    }
    
    public int getRequiredLevel() {
        return requiredLevel;
    }
    
    public Potion brew() {
        return potion;
    }
    
    public boolean matches(List<String> inputIngredients) {
        if (inputIngredients.size() != ingredients.size()) {
            return false;
        }
        
        // Make a copy of the lists so we can sort them
        List<String> sortedInput = new ArrayList<>(inputIngredients);
        List<String> sortedRecipe = new ArrayList<>(ingredients);
        
        Collections.sort(sortedInput);
        Collections.sort(sortedRecipe);
        
        return sortedInput.equals(sortedRecipe);
    }
}