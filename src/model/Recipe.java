package model;
import DSA.*;


public class Recipe {
    private ArrayList<String> ingredients;
    private Potion potion;
    private int requiredLevel;
    
    public Recipe(ArrayList<String> ingredients, Potion potion, int requiredLevel) {
        this.ingredients = ingredients;
        this.potion = potion;
        this.requiredLevel = requiredLevel;
    }
    
    public ArrayList<String> getIngredients() {
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
    
    public boolean matches(ArrayList<String> inputIngredients) {
        if (inputIngredients.size() != ingredients.size()) {
            return false;
        }
        
        // Sort both lists directly
        ingredients.bubbleSort();
        inputIngredients.bubbleSort();
        
        return ingredients.equals(inputIngredients);
    }
}