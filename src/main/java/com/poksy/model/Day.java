package com.poksy.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a day in the habit tracking system.
 */
public record Day(
        String id,
        String name,
        LocalDate date
) {
    
    private static final DateTimeFormatter NAME_FORMATTER = 
            DateTimeFormatter.ofPattern("EEE d MMM", Locale.ENGLISH);
    
    /**
     * Creates a Day for today with an auto-generated name.
     */
    public static Day forToday() {
        LocalDate today = LocalDate.now();
        String name = today.format(NAME_FORMATTER);
        return new Day(null, name, today);
    }
    
    /**
     * Creates a Day for a specific date with an auto-generated name.
     */
    public static Day forDate(LocalDate date) {
        String name = date.format(NAME_FORMATTER);
        return new Day(null, name, date);
    }
    
    /**
     * Returns a new Day with the specified ID.
     */
    public Day withId(String id) {
        return new Day(id, this.name, this.date);
    }
}
