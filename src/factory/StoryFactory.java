package factory;

import model.Monster;

public class StoryFactory {
    public static Monster generateRandomMonster(int playerLevel) {
        // Simpler monsters at low levels, stronger ones at high levels
        if (playerLevel >= 1 && playerLevel <= 10) {
            // Easy level monsters
            String[] monsters = {"Slime", "Rat", "Bat", "Spider"};
            String[] drops = {"Slime Goo", "Rat Tail", "Bat Wing", "Spider Web"};
            int idx = (int)(Math.random() * monsters.length);
            return new Monster(monsters[idx], 10 + playerLevel, drops[idx], 5, 8);
        } else if (playerLevel >= 11 && playerLevel <= 20) {
            // Intermediate level monsters
            String[] monsters = {"Goblin", "Orc", "Wolf", "Skeleton"};
            String[] drops = {"Goblin Ear", "Orc Tooth", "Wolf Fang", "Bone Dust"};
            int idx = (int)(Math.random() * monsters.length);
            return new Monster(monsters[idx], 20 + playerLevel, drops[idx], 10, 15);
        } else if (playerLevel >= 21 && playerLevel <= 30) {
            // Expert level monsters
            String[] monsters = {"Dragon", "Chimera", "Griffin", "Basilisk"};
            String[] drops = {"Dragon Scale", "Chimera Horn", "Griffin Feather", "Basilisk Eye"};
            int idx = (int)(Math.random() * monsters.length);
            return new Monster(monsters[idx], 35 + playerLevel, drops[idx], 20, 25);
        } else if (playerLevel >= 31 && playerLevel <= 40) {
            // Hard level monsters
            String[] monsters = {"Ancient Dragon", "Phoenix", "Hydra", "Behemoth"};
            String[] drops = {"Ancient Scale", "Phoenix Ash", "Hydra Blood", "Behemoth Horn"};
            int idx = (int)(Math.random() * monsters.length);
            return new Monster(monsters[idx], 50 + playerLevel, drops[idx], 30, 35);
        } else {
            return new Monster("Slime", 5, "Slime Goo", 2, 5); // Default for invalid levels
        }
    }
}
