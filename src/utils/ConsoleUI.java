package utils;

import model.Ingredient;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner sc = new Scanner(System.in);

    public static void showWelcome() {
        System.out.println("🔮 Welcome to PotionMaster: The Java Brew Simulation!");
    }

    public static String getPlayerName() {
        System.out.print("Enter your name: ");
        return sc.nextLine();
    }

    public static void showMenu() {
        System.out.println("\n1. Start Mixing Potions");
        System.out.println("2. View Player Stats");
        System.out.println("3. View Brewed Potions");
        System.out.println("4. View Inventory"); // New option to view inventory
        System.out.println("5. View Ingredient Stack");
        System.out.println("6. Play Game"); // New option to view ingredient stack
        System.out.println("7. Exit");

        System.out.print("Choose: ");
    }

    public static void GameMenu() {
        System.out.println("1. 🤺Fight");
        System.out.println("2. Player Stats");
        System.out.println("3. Exit");

        System.out.print("Choose: ");
    }

    public static int getChoice() {
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the remaining newline
        return choice;
    }
    // Based on your code and placeholder location, it seems there was a question about fixing a loop, but I don't see the loop implementation in the provided code. To properly help, I would need:

    // 1. The loop code that's breaking
    // 2. The specific issue or error you're experiencing
    // 3. What you're trying to accomplish with the loop

    // The placeholder is positioned between `getChoice()` and `displayIngredients()` methods in the ConsoleUI class. If you share the problematic loop code and describe the issue, I can help fix it.
    public static void displayIngredients(List<Ingredient> list) {
        System.out.println("\n🧪 Available Ingredients:");
        int i = 1;
        for (Ingredient ing : list) {
            System.out.println(i++ + ". " + ing);
        }

    }

    public static void showExit() {
        System.out.println("🧪 Thanks for playing PotionMaster. Goodbye!");
    }
}
