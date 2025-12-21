package com.poksy;

import com.poksy.automation.DailyHabitAutomation;
import com.poksy.client.NotionClient;
import com.poksy.config.NotionConfig;

/**
 * Main entry point for Notion automations.
 * 
 * Usage:
 *   java -jar notion-automations.jar [command]
 * 
 * Commands:
 *   habits  - Run the daily habit automation (default)
 *   help    - Show this help message
 */
public class App {
    
    public static void main(String[] args) {
        String command = args.length > 0 ? args[0] : "habits";
        
        switch (command.toLowerCase()) {
            case "habits" -> runHabitsAutomation();
            case "help" -> printHelp();
            default -> {
                System.err.println("Unknown command: " + command);
                printHelp();
                System.exit(1);
            }
        }
    }
    
    private static void runHabitsAutomation() {
        try {
            // Validate configuration
            NotionConfig.validate();
            
            // Run automation
            NotionClient client = new NotionClient();
            DailyHabitAutomation automation = new DailyHabitAutomation(client);
            automation.run();
            
        } catch (IllegalStateException e) {
            System.err.println("❌ Configuration error: " + e.getMessage());
            System.err.println("\nMake sure you have set the following environment variables:");
            System.err.println("  - NOTION_TOKEN");
            System.err.println("  - DAYS_DATABASE_ID");
            System.err.println("  - DAILY_LOG_DATABASE_ID");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void printHelp() {
        System.out.println("""
                
                Notion Automations
                ==================
                
                Usage: java -jar notion-automations.jar [command]
                
                Commands:
                  habits  - Run the daily habit automation (default)
                  help    - Show this help message
                
                Environment Variables:
                  NOTION_TOKEN          - Your Notion integration token
                  DAYS_DATABASE_ID      - ID of the Days database
                  DAILY_LOG_DATABASE_ID - ID of the Daily Log database
                
                You can also create a .env file in the project root with these values.
                """);
    }
}
