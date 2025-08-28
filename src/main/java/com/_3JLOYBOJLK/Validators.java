package com._3JLOYBOJLK;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;

public class Validators {

    //add validate symbols and length(have 1 func)
    public static String validateTitle(String title) {
        return (title != null && !title.trim().isEmpty()) ? title.trim() : "Unknown";
    }

    public static int validateYear(int year) {
        int currentYear = Year.now().getValue();
        return (year >= 1930 && year <= currentYear) ? year : 1930;
    }
    //add validate symbols and length(have 1 func)
    public static String validateDirector(String director) {
        return (director != null && !director.trim().isEmpty()) ? director.trim() : "Unknown";
    }
    //add validate symbols and length(have 1 func)
    public static String validateGenre(String genre) {
        return (genre != null && !genre.trim().isEmpty()) ? genre.trim() : "Unknown";
    }

    public static double validateRating(double rating) {
        return (rating >= 0.0 && rating <= 10.0) ? rating : 0.0;
    }

    public static String validateFile(Scanner sc) {
        System.out.println("Enter name of CSV File");
        while (true) {
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌Error: Filename cannot be empty. Try again.");
                continue;
            }

            if(containtsInvalidSymbols(input)){
                System.out.println("❌Error: Filename contains invalid symbols. Try again:");
                continue;
            }

            String formatingName = formatFileName(input);
            File file = new File(formatingName);

            if (file.exists()) {
                System.out.println("❌Error: File already exists. He will be overwritten.");
            }
            return formatingName;
        }
    }

    private static boolean containtsInvalidSymbols(String filename){
        char[] invalidSymbols ={'<', '>', ':', '"', '/', '\\', '|', '?', '*'};
        for (char ch : invalidSymbols ) {
            if (filename.contains(String.valueOf(ch))) {
                return true;
            }
        }
        return false;
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
    public static String checkerString(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if(hasDigit(input)){
                System.out.println("❌ Title cannot has digit ! Please try again.");
                continue;
            }
            String result = Validators.validateTitle(input);

            if (!"Unknown".equals(result)) {
                return result;
            }
            System.out.println("❌ Title cannot be empty! Please try again.");
        }
    }

    private static boolean hasDigit(String input) {
        for(char c: input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static int checkerYear(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Year cannot be empty!");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                int validatedYear = Validators.validateYear(value);

                if (validatedYear != 1930) {
                    return validatedYear;
                }

                System.out.println("❌ Year must be between 1930 and " + Year.now().getValue());

            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    public static double checkerRating(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Rating cannot be empty!");
                continue;
            }

            try {
                double value = Double.parseDouble(input);
                double validatedRating = Validators.validateRating(value);

                if (validatedRating != 0.0) {
                    return validatedRating;
                }

                System.out.println("❌ Rating must be between 0.0 and 10.0");

            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
}