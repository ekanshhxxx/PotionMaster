package main;

import java.io.IOException;
import utils.InputManager;

import game.GameEngine;
import main.account.LoginManager;
import main.account.PlayerAccount;

public class PotionMaster {
    public static void main(String[] args) {
        // Create login manager and show login screen
        LoginManager loginManager = new LoginManager();
        PlayerAccount account = loginManager.showLoginScreen();
        
        GameEngine game;
        if (account != null) {
            // Start game with logged-in account
            game = new GameEngine(account, loginManager.getAccountManager());
            
            // Add shutdown hook for unexpected termination
            final PlayerAccount finalAccount = account;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nEmergency cleanup: Saving game state...");
                try {
                    // This properly logs out and updates session info
                    loginManager.getAccountManager().logout(finalAccount.getUsername());
                } catch (Exception e) {
                    System.err.println("Error during emergency save: " + e.getMessage());
                }
                InputManager.close(); // Close scanner resources
            }));
        } else {
            // Start game without login (guest mode)
            game = new GameEngine();
        }
        
        game.start();
        
        // When game ends normally, call logout instead of just saving
        if (account != null) {
            try {
                // This properly logs out and updates session tracking
                loginManager.getAccountManager().logout(account.getUsername());
            } catch (Exception e) {
                System.err.println("Error during logout: " + e.getMessage());
                
                // Fallback to just saving if logout fails
                try {
                    loginManager.getAccountManager().saveAccount(account);
                    System.out.println("Your progress has been saved.");
                } catch (IOException saveEx) {
                    System.err.println("Error saving account data: " + saveEx.getMessage());
                }
            }
        }
        
        InputManager.close(); // Ensure scanner is closed when game ends normally
    }
}