package factory;

import model.Potion;
import model.Recipe;

import java.util.*;

public class RecipeFactory {
    private final List<Potion> potionBook = new ArrayList<>();
    private final List<Recipe> recipes = new ArrayList<>();

    public RecipeFactory() {
        // Predefined recipes
<<<<<<< HEAD
        createPotion("Invisibility Potion", List.of("Moon Dust", "Phoenix Feather"), 2, 10, "Makes the drinker invisible for a short time");
        createPotion("Fire Shield", List.of("Dragon Scale", "Wolfsbane"), 3, 20, "Provides protection against fire damage");
        createPotion("Energy Blast", List.of("Mandrake Root", "Wolfsbane"), 1, 25, "Creates a surge of offensive energy");
        
        // 🔓 High-Level Ingredient Recipes
        createPotion("Speed Surge", List.of("Goblin Ear", "Moon Dust"), 2, 30, "Greatly increases movement speed");
        createPotion("Venom Vial", List.of("Basilisk Fang", "Mandrake Root"), 3, 35, "Inflicts poison damage over time");
        createPotion("Ocean's Grace", List.of("Mermaid Tears", "Phoenix Feather"), 4, 40, "Allows breathing underwater");
        createPotion("Revive Shield", List.of("Phoenix Feather", "Wolfsbane"), 4, 50, "Revives the user when fatally wounded");
        createPotion("Nightmare Brew", List.of("Moon Dust", "Goblin Ear"), 3, 55, "Causes terrifying hallucinations to enemies");
    }
    
    private void createPotion(String name, List<String> ingredients, int difficulty, int damage, String effect) {
        // Create the potion with the effect
        Potion potion = new Potion(name, ingredients, difficulty, damage, effect);
        potionBook.add(potion);
        
        // Also create a recipe for this potion (required level based on difficulty)
        Recipe recipe = new Recipe(ingredients, potion, difficulty * 2);
        recipes.add(recipe);
=======
        potionBook.add(new Potion("Invisibility Potion", List.of("Moon Dust", "Phoenix Feather"), 2,10));
        potionBook.add(new Potion("Fire Shield", List.of("Dragon Scale", "Wolfsbane"), 3,20));
        potionBook.add(new Potion("Energy Blast", List.of("Mandrake Root", "Wolfsbane"), 1,25));
        // 🔓 High-Level Ingredient Recipes
        potionBook.add(new Potion("Speed Surge", List.of("Goblin Ear", "Moon Dust"), 2,30));
        potionBook.add(new Potion("Venom Vial", List.of("Basilisk Fang", "Mandrake Root"), 3,35));
        potionBook.add(new Potion("Ocean's Grace", List.of("Mermaid Tears", "Phoenix Feather"), 4,40));
        potionBook.add(new Potion("Revive Shield", List.of("Phoenix Feather", "Wolfsbane"), 4,50));
        potionBook.add(new Potion("Nightmare Brew", List.of("Moon Dust", "Goblin Ear"), 3,55));
>>>>>>> 8afef9687c7cdae725db756cf7d3433f1997f942
    }

    public Potion getPotionByIngredients(List<String> ingredients) {
        // Sort the ingredients for consistent matching
        List<String> sortedIngredients = new ArrayList<>(ingredients);
        Collections.sort(sortedIngredients);
        
        for (Potion p : potionBook) {
            // Get and sort the required ingredients
            List<String> requiredIngredients = new ArrayList<>(p.getRequiredIngredients());
            Collections.sort(requiredIngredients);
            
            if (requiredIngredients.equals(sortedIngredients)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Returns all available recipes
     * @return List of all recipes
     */
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes);
    }
    
    /**
     * Gets a potion by its name
     * @param name The name of the potion to find
     * @return The found potion or null if not found
     */
    public Potion getPotionByName(String name) {
        for (Potion p : potionBook) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Gets all available potions
     * @return List of all potions
     */
    public List<Potion> getAllPotions() {
        return new ArrayList<>(potionBook);
    }
}