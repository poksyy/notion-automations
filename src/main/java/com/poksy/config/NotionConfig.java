package com.poksy.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Configuration holder for Notion API credentials and database IDs.
 * Loads values from environment variables or .env file.
 */
public class NotionConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    // API Configuration
    public static final String NOTION_API_URL = "https://api.notion.com/v1";
    public static final String NOTION_VERSION = "2025-09-03";

    // Credentials (from environment variables)
    public static final String NOTION_TOKEN = getEnv("NOTION_TOKEN");

    // Database IDs
    public static final String DAYS_DATABASE_ID = getEnv("DAYS_DATABASE_ID");
    public static final String DAILY_LOG_DATABASE_ID = getEnv("DAILY_LOG_DATABASE_ID");

    /**
     * Gets an environment variable, first checking system env, then .env file.
     */
    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            value = dotenv.get(key);
        }
        return value;
    }

    /**
     * Validates that all required configuration is present.
     */
    public static void validate() {
        if (NOTION_TOKEN == null || NOTION_TOKEN.isEmpty()) {
            throw new IllegalStateException("NOTION_TOKEN is not configured");
        }
        if (DAYS_DATABASE_ID == null || DAYS_DATABASE_ID.isEmpty()) {
            throw new IllegalStateException("DAYS_DATABASE_ID is not configured");
        }
        if (DAILY_LOG_DATABASE_ID == null || DAILY_LOG_DATABASE_ID.isEmpty()) {
            throw new IllegalStateException("DAILY_LOG_DATABASE_ID is not configured");
        }
    }
}