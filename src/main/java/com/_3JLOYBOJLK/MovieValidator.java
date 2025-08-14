package com._3JLOYBOJLK;

import java.time.Year;

public class MovieValidator {

    public static String validateTitle(String title) {
        return (title != null && !title.trim().isEmpty()) ? title.trim() : "Unknown";
    }

    public static int validateYear(int year) {
        int currentYear = Year.now().getValue();
        return (year >= 1950 && year <= currentYear) ? year : 1950;
    }

    public static String validateDirector(String director) {
        return (director != null && !director.trim().isEmpty()) ? director.trim() : "Unknown";
    }

    public static String validateGenre(String genre) {
        return (genre != null && !genre.trim().isEmpty()) ? genre.trim() : "Unknown";
    }

    public static double validateRating(double rating) {
        return (rating >= 0.0 && rating <= 10.0) ? rating : 0.0;
    }
}