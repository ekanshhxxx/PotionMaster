package main.account;

import java.util.Scanner;

public class LoginManager {
    private AccountManager accountManager;
    private Scanner scanner;
    private PlayerAccount currentAccount;
    
    public LoginManager() {
        this.accountManager = new AccountManager();
        this.scanner = new Scanner(System.in);
    }
    
    // in colsole .ui
    public PlayerAccount showLoginScreen() {
        while (true) {
            System.out.println("===== POTION MASTER =====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    currentAccount = handleLogin();
                    if (currentAccount != null) {
                        return currentAccount;
                    }
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private PlayerAccount handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        PlayerAccount account = accountManager.login(username, password);
        
        if (account == null) {
            System.out.println("Login failed. Check your username and password.");
            return null;
        }
        
        System.out.println("Login successful! Welcome back, " + username + "!");
        return account;
    }
    
    private void handleRegistration() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();
        
        if (accountManager.registerAccount(username, password)) {
            System.out.println("Account created successfully! You can now login.");
        } else {
            System.out.println("Registration failed. Username might already be taken.");
        }
    }
    
    public void logout() {
        if (currentAccount != null) {
            accountManager.logout(currentAccount.getUsername());
            currentAccount = null;
            System.out.println("You have been logged out.");
        }
    }
    
    public AccountManager getAccountManager() {
        return accountManager;
    }
}