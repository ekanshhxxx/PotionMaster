package utils;

import java.util.Scanner;

public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Get access to the scanner directly if needed.
     * @return The singleton Scanner instance
     */
    public static Scanner getScanner() {
        return scanner;
    }
    
    /**
     * Read an integer from input.
     * @return The integer value entered by user
     */
    public static int readInt() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            return value;
        } catch (Exception e) {
            clearBuffer(); // Clear buffer on error
            throw e; // Re-throw to be handled by caller
        }
    }
    
    /**
     * Read an integer with a default value if input is invalid.
     * @param defaultValue Value to return if input is invalid
     * @return The entered integer or default value
     */
    public static int readIntWithDefault(int defaultValue) {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            return value;
        } catch (Exception e) {
            clearBuffer();
            return defaultValue;
        }
    }
    
    /**
     * Read a complete line of text.
     * @return The line entered by user
     */
    public static String readLine() {
        return scanner.nextLine();
    }
    
    /**
     * Read a single word (string without spaces).
     * @return The string entered by user
     */
    public static String readString() {
        try {
            String value = scanner.next();
            scanner.nextLine(); // Clear buffer
            return value;
        } catch (Exception e) {
            clearBuffer();
            return "";
        }
    }
    
    /**
     * Clear the scanner buffer.
     */
    public static void clearBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
    
    /**
     * Read a double value from input.
     * @return The double value entered by user
     */
    public static double readDouble() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine(); // Clear buffer
            return value;
        } catch (Exception e) {
            clearBuffer();
            throw e;
        }
    }
    
    /**
     * Read a boolean value from input (y/n).
     * @return true for y/yes, false for anything else
     */
    public static boolean readBoolean() {
        String input = readString().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
    
    /**
     * Close the scanner. Should only be called at application exit.
     */
    public static void close() {
        scanner.close();
    }
}