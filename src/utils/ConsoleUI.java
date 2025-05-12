
package utils;

import model.Ingredient;
import DSA.*;

public class ConsoleUI {
    // Remove the static Scanner field and use InputManager instead

    public static void showWelcome() {
        System.out.println("💎 Welcome to PotionMaster: The Java Brew Simulation!");
    }

    public static String getPlayerName() {
        System.out.print("Enter your name: ");
        return InputManager.readLine();
    }

    // public static void showMenu() {
    //     System.out.println(ConsoleColors.CYAN_BOLD + "\n┌───────────────── MAIN MENU ─────────────────┐" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│                                              │" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "1." + ConsoleColors.GREEN + " 🧪 Start Mixing Potions                  " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "2." + ConsoleColors.GREEN + " 📊 View Player Stats                     " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "3." + ConsoleColors.GREEN + " 🧫 View Brewed Potions                   " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "4." + ConsoleColors.GREEN + " 🎒 View Inventory                        " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "5." + ConsoleColors.GREEN + " 📦 View Ingredient Stack                 " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "6." + ConsoleColors.GREEN + " 🎮 Play Game                             " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│  " + ConsoleColors.YELLOW_BOLD + "7." + ConsoleColors.GREEN + " 🚪 Exit                                  " + ConsoleColors.CYAN + "│" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN + "│                                              │" + ConsoleColors.RESET);
    //     System.out.println(ConsoleColors.CYAN_BOLD + "└──────────────────────────────────────────────┘" + ConsoleColors.RESET);
    //     System.out.print(ConsoleColors.YELLOW_BOLD + "Choose: " + ConsoleColors.RESET);
    // }

    // Update the showMenu method
public static void showMenu() {
    System.out.println("\n===== MAIN MENU =====");
    System.out.println("1. Brew a Potion");
    System.out.println("2. View Player Stats");
    System.out.println("3. View Brewed Potions");
    System.out.println("4. Shop (Coming soon)");
    System.out.println("5. View Inventory");
    System.out.println("6. Game Menu");
    System.out.println("7. Quest Board");   // NEW: Quest board option
    System.out.println("8. Exit Game");     // Changed from 7 to 8
    System.out.print("Choose an option: ");
}

    public static void GameMenu() {
        System.out.println("1. 🧚 Fight");
        System.out.println("2. Player Stats");
        System.out.println("3. Exit");

        System.out.print("Choose: ");
    }

    public static int getChoice() {
        try {
            int choice = InputManager.readInt();
            return choice;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            InputManager.clearBuffer(); // Clear scanner buffer
            return -1; // Return invalid choice
        }
    }

    public static String getString() {
        return InputManager.readString();
    }

    public static String getLine() {
        return InputManager.readLine();
    }
    // Based on your code and placeholder location, it seems there was a question about fixing a loop, but I don't see the loop implementation in the provided code. To properly help, I would need:

    // 1. The loop code that's breaking
    // 2. The specific issue or error you're experiencing
    // 3. What you're trying to accomplish with the loop

    // The placeholder is positioned between `getChoice()` and `displayIngredients()` methods in the ConsoleUI class. If you share the problematic loop code and describe the issue, I can help fix it.
    public static void displayIngredients(ArrayList<Ingredient> list) {
        System.out.println("\n🧪 Available Ingredients:");
        int i = 1;
        for (Ingredient ing : list) {
            System.out.println(i++ + ". " + ing);
        }
    }

    public static void showExit() {
        System.out.println("🧪 Thanks for playing PotionMaster. Goodbye!");
    }
    
    public static void showError(String message) {
        System.out.println("❌ Error: " + message);
    }
    
    public static void showSuccess(String message) {
        System.out.println("✅ " + message);
    }
    
    public static void showTitle(String title) {
        System.out.println("\n===== " + title + " =====");
    }
    
    public static void showLoginMenu() {
        System.out.println("===== POTION MASTER =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }
    
    public static void showProgress(String message) {
        System.out.println("⏳ " + message);
    }
    
    public static boolean confirm(String message) {
        System.out.print(message + " (y/n): ");
        String response = InputManager.readString().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
}