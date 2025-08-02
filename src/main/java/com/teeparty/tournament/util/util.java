package com.teeparty.tournament.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class util {
    public static LocalDateTime parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;  // or your custom format
            return LocalDateTime.parse(dateStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'T'HH:mm:ss'.");
        }
    }
}
