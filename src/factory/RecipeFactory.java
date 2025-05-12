package factory;

import DSA.*;
import model.Potion;
import model.Recipe;



public class RecipeFactory {
    private final ArrayList<Potion> potionBook = new ArrayList<>();
    private final ArrayList<Recipe> recipes = new ArrayList<>();

    public RecipeFactory() {
        // Predefined recipes
        createPotion("Invisibility Potion", ArrayList.of("Moon Dust", "Phoenix Feather"), 2, 10, "Makes the drinker invisible for a short time");
        createPotion("Fire Shield", ArrayList.of("Dragon Scale", "Wolfsbane"), 3, 20, "Provides protection against fire damage");
        createPotion("Energy Blast", ArrayList.of("Mandrake Root", "Wolfsbane"), 1, 25, "Creates a surge of offensive energy");
        createHealingPotion("Healing Elixir",ArrayList.of("Dragon Scale", "Phoenix Feather"), 1,0 ,30, "Heals a small amount of health");
        
        // 🔓 High-Level Ingredient Recipes
        createPotion("Speed Surge", ArrayList.of("Goblin Ear", "Moon Dust"), 2, 30, "Greatly increases movement speed");
        createPotion("Venom Vial", ArrayList.of("Basilisk Fang", "Mandrake Root"), 3, 35, "Inflicts poison damage over time");
        createPotion("Ocean's Grace", ArrayList.of("Mermaid Tears", "Phoenix Feather"), 4, 40, "Allows breathing underwater");
        createPotion("Revive Shield", ArrayList.of("Phoenix Feather", "Wolfsbane"), 4, 50, "Revives the user when fatally wounded");
        createPotion("Nightmare Brew", ArrayList.of("Moon Dust", "Goblin Ear"), 3, 55, "Causes terrifying hallucinations to enemies");
    }
    
    private void createPotion(String name, ArrayList<String> ingredients, int difficulty, int damage, String effect) {
        // Create the potion with the effect
        Potion potion = new Potion(name, ingredients, difficulty, damage, effect);
        potionBook.add(potion);
        
        // Also create a recipe for this potion (required level based on difficulty)
        Recipe recipe = new Recipe(ingredients, potion, difficulty * 2);
        recipes.add(recipe);
    }
    private  void createHealingPotion(String name,ArrayList<String> ingredients,int difficulty ,int damage, int incHealth, String effect) {
        // Create the potion with the effect
        Potion potion = new Potion(name, ingredients, difficulty, damage, incHealth, effect);
        potionBook.add(potion);
        
        // Also create a recipe for this potion (required level based on difficulty)
        Recipe recipe = new Recipe(ingredients, potion, difficulty * 2);
        recipes.add(recipe);
    }

    public Potion getPotionByIngredients(ArrayList<String> ingredients) {
        // Sort the ingredients for consistent matching
        ArrayList<String> sortedIngredients = new ArrayList<>(ingredients);
        sortedIngredients.bubbleSort();
        System.out.println("Sorted Ingredients: " + sortedIngredients);
        
        for (Potion p : potionBook) {
            // Get and sort the required ingredients
            ArrayList<String> requiredIngredients = new ArrayList<>(p.getRequiredIngredients());
            requiredIngredients.bubbleSort();
            // Debug comparison
            if (sortedIngredients.size() != requiredIngredients.size()) {
                
                continue;
            }
            
            boolean allMatch = true;
            for (int i = 0; i < sortedIngredients.size(); i++) {
                if (!sortedIngredients.get(i).equals(requiredIngredients.get(i))) {
                    allMatch = false;
                    break;
                }
            }
            
            if (allMatch) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Returns all available recipes
     * @return List of all recipes
     */
    public ArrayList<Recipe> getAllRecipes() {
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
    public ArrayList<Potion> getAllPotions() {
        return new ArrayList<>(potionBook);
    }
}