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
        System.out.println("3. Exit");
        System.out.println("4. View Brewed Potions");

        System.out.print("Choose: ");
    }

    public static int getChoice() {
        return sc.nextInt();
    }

    public static void displayIngredients(List<Ingredient> list) {
        System.out.println("\n🧺 Available Ingredients:");
        int i = 1;
        for (Ingredient ing : list) {
            System.out.println(i++ + ". " + ing);
        }

    }

    public static void showExit() {
        System.out.println("🧪 Thanks for playing PotionMaster. Goodbye!");
    }
}
