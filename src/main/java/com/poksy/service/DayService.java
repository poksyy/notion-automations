package com.poksy.service;

import com.poksy.client.NotionClient;
import com.poksy.config.NotionConfig;
import com.poksy.model.Day;

import java.util.Optional;

/**
 * Service for managing Day entries in Notion.
 */
public class DayService {
    
    private final NotionClient client;
    
    public DayService(NotionClient client) {
        this.client = client;
    }
    
    /**
     * Creates a new Day entry in the Days database.
     * 
     * @param day The day to create
     * @return The created Day with its Notion page ID, or empty if failed
     */
    public Optional<Day> create(Day day) {
        String json = """
                {
                    "parent": { "database_id": "%s" },
                    "properties": {
                        "Name": {
                            "title": [{ "text": { "content": "%s" } }]
                        },
                        "Date": {
                            "date": { "start": "%s" }
                        }
                    }
                }
                """.formatted(
                        NotionConfig.DAYS_DATABASE_ID,
                        day.name(),
                        day.date().toString()
                );
        
        return client.createPage(json)
                .map(day::withId);
    }
}
