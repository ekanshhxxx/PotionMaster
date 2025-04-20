package model;

public class Ingredient {
    private String name;
    private String effect;

    public Ingredient(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + " (" + effect + ")";
    }
}
