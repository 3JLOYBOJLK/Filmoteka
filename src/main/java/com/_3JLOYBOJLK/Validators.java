package com._3JLOYBOJLK;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;

public class Validators {

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

    public static String validateFile(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Filename cannot be empty. Try again.");
                continue;
            }
            String formatingName = formatFileName(input.trim());
            try {
                File file = new File(formatingName);
                if (file.exists()) {
                    System.out.println("File already exists. He will be overwritten.");
                }
                return formatingName;
            } catch (Exception e) {
                System.out.println("Invalid filename or path. Try again:");
            }
        }
    }

    private static String formatFileName(String fileName) {

        if (fileName.toLowerCase().endsWith(".csv")) {
            return fileName;
        } else if (fileName.endsWith(".")) {
            return fileName + "csv";
        } else if (fileName.contains(".") && !fileName.endsWith(".csv")) {
            return fileName + ".csv";
        } else {
            return fileName + ".csv";
        }
    }
}