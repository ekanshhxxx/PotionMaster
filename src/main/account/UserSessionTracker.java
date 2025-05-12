package main.account;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import DSA.*;


public class UserSessionTracker {
    private static final String SESSION_LOG_FILE = "SESSIONS/session_logs.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static {
        // Create directories if they don't exist
        File dir = new File("playerdata");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Create log file if it doesn't exist
        File logFile = new File(SESSION_LOG_FILE);
        if (!logFile.exists()) {
            try {
                ArrayList<UserSession> emptySessions = new ArrayList<>();
                saveSessionLogs(emptySessions);
            } catch (IOException e) {
                System.err.println("Error initializing session log file: " + e.getMessage());
            }
        }
    }
    
    public static class UserSession {
        private String username;
        private String loginTime;
        private String logoutTime;
        private int sessionDurationMinutes;
        private int xpGained;
        private int levelsGained;
        
        public UserSession(String username) {
            this.username = username;
            this.loginTime = dateFormat.format(new Date());
            this.logoutTime = null;
            this.sessionDurationMinutes = 0;
            this.xpGained = 0;
            this.levelsGained = 0;
        }
        
        public void closeSession(int xpGained, int levelsGained) {
            this.logoutTime = dateFormat.format(new Date());
            
            // Calculate session duration in minutes
            try {
                Date login = dateFormat.parse(loginTime);
                Date logout = dateFormat.parse(logoutTime);
                long durationMs = logout.getTime() - login.getTime();
                this.sessionDurationMinutes = (int)(durationMs / (1000 * 60));
            } catch (Exception e) {
                this.sessionDurationMinutes = 0;
            }
            
            this.xpGained = xpGained;
            this.levelsGained = levelsGained;
        }
        
        @Override
        public String toString() {
            return "Session{" +
                   "user='" + username + '\'' +
                   ", login=" + loginTime +
                   ", logout=" + logoutTime +
                   ", duration=" + sessionDurationMinutes + " min" +
                   ", xp=" + xpGained +
                   ", levels=" + levelsGained +
                   '}';
        }
    }
    
    public static UserSession createSession(String username) throws IOException {
        ArrayList<UserSession> sessions = loadSessionLogs();
        UserSession newSession = new UserSession(username);
        sessions.add(newSession);
        saveSessionLogs(sessions);
        return newSession;
    }
    
    public static void closeSession(String username, int xpGained, int levelsGained) throws IOException {
        ArrayList<UserSession> sessions = loadSessionLogs();
        
        // Find the user's most recent open session
        for (int i = sessions.size() - 1; i >= 0; i--) {
            UserSession session = sessions.get(i);
            if (session.username.equals(username) && session.logoutTime == null) {
                session.closeSession(xpGained, levelsGained);
                break;
            }
        }
        
        saveSessionLogs(sessions);
    }
    
    private static ArrayList<UserSession> loadSessionLogs() throws IOException {
        ArrayList<UserSession> sessions = new ArrayList<>();
        File file = new File(SESSION_LOG_FILE);
        
        if (!file.exists() || file.length() == 0) {
            return sessions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Format: username|loginTime|logoutTime|duration|xpGained|levelsGained
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    UserSession session = new UserSession(parts[0]);
                    session.loginTime = parts[1];
                    session.logoutTime = parts[2].equals("null") ? null : parts[2];
                    session.sessionDurationMinutes = Integer.parseInt(parts[3]);
                    session.xpGained = Integer.parseInt(parts[4]);
                    session.levelsGained = Integer.parseInt(parts[5]);
                    sessions.add(session);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading session log: " + e.getMessage());
        }
        
        return sessions;
    }
    
    private static void saveSessionLogs(ArrayList<UserSession> sessions) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SESSION_LOG_FILE))) {
            for (UserSession session : sessions) {
                // Format: username|loginTime|logoutTime|duration|xpGained|levelsGained
                writer.println(
                    session.username + "|" + 
                    session.loginTime + "|" + 
                    (session.logoutTime == null ? "null" : session.logoutTime) + "|" + 
                    session.sessionDurationMinutes + "|" + 
                    session.xpGained + "|" + 
                    session.levelsGained
                );
            }
        }
    }
    
    // Utility method to view session logs - helpful for debugging
    public static void viewSessionLogs() {
        try {
            ArrayList<UserSession> sessions = loadSessionLogs();
            System.out.println("\n===== SESSION LOGS =====");
            if (sessions.isEmpty()) {
                System.out.println("No sessions recorded.");
            } else {
                for (UserSession session : sessions) {
                    System.out.println(session);
                }
            }
            System.out.println("=======================\n");
        } catch (IOException e) {
            System.err.println("Error viewing session logs: " + e.getMessage());
        }
    }
}