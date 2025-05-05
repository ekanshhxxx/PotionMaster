package model;

public class PlayerRank {
    
    public static String getRankTitle(int level) {
        if (level >= 46) return "Grandmaster Alchemage";
        if (level >= 41) return "Potion Sage";
        if (level >= 36) return "Elixir Warden";
        if (level >= 31) return "Master Brewer";
        if (level >= 26) return "Arcane Alchemist";
        if (level >= 21) return "Infusion Adept";
        if (level >= 16) return "Battle Chemist";
        if (level >= 11) return "Combat Mixer";
        if (level >= 6) return "Apprentice Brewer";
        return "Novice Alchemist";
    }
    
    public static String getTierName(int level) {
        if (level >= 46) return "Divine Tier";
        if (level >= 41) return "Mythic Tier";
        if (level >= 36) return "Legendary Tier";
        if (level >= 31) return "Mastery Tier";
        if (level >= 26) return "Elite Tier";
        if (level >= 21) return "Specialist Tier";
        if (level >= 16) return "Adept Tier";
        if (level >= 11) return "Journeyman Tier";
        if (level >= 6) return "Apprentice Tier";
        return "Initiation Tier";
    }
}