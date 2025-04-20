package factory;

import model.Potion;

import java.util.*;

public class RecipeFactory {
    private final List<Potion> potionBook = new ArrayList<>();

    public RecipeFactory() {
        // Predefined recipes
        potionBook.add(new Potion("Invisibility Potion", List.of("Moon Dust", "Phoenix Feather"), 2));
        potionBook.add(new Potion("Fire Shield", List.of("Dragon Scale", "Wolfsbane"), 3));
        potionBook.add(new Potion("Energy Blast", List.of("Mandrake Root", "Wolfsbane"), 1));
        // 🔓 High-Level Ingredient Recipes
        potionBook.add(new Potion("Speed Surge", List.of("Goblin Ear", "Moon Dust"), 2));
        potionBook.add(new Potion("Venom Vial", List.of("Basilisk Fang", "Mandrake Root"), 3));
        potionBook.add(new Potion("Ocean's Grace", List.of("Mermaid Tears", "Phoenix Feather"), 4));
        potionBook.add(new Potion("Revive Shield", List.of("Phoenix Feather", "Wolfsbane"), 4));
    }

    public Potion getPotionByIngredients(List<String> ingredients) {
        for (Potion p : potionBook) {
            if (p.getRequiredIngredients().equals(ingredients)) {
                return p;
            }
        }
        return null;
    }
}
