package model;
public class Monster {
    private String name;
    private int health;
    private String rewardIngredient;
    private int credsReward;
    private int attackDamage;

    public Monster(String name, int health, String rewardIngredient, int credsReward, int attackDamage) {
        this.name = name;
        this.health = health;
        this.rewardIngredient = rewardIngredient;
        this.credsReward = credsReward;
        this.attackDamage = attackDamage;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public String getRewardIngredient() { return rewardIngredient; }
    public int getCredsReward() { return credsReward; }

    public void damageMonster(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0; // Ensure health doesn't go below 0
        }
    }
    public int attack() {
        return attackDamage; // Return the attack damage of the monster
        
    }
    public boolean isAlive() {
        return health > 0;
    }
}
