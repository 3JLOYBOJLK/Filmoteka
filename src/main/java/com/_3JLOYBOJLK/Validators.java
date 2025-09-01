package com._3JLOYBOJLK;

import java.io.File;
import java.time.Year;
import java.util.Scanner;

public class Validators {

    //add validate symbols and length(have 1 func)
    public static String validateTitle(String title) {
        return (title != null && !title.trim().isEmpty())? title.trim() : "Unknown";
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
        while (true) {
            System.out.println("Enter name of CSV File");
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
            File dir = new File(AppConfiguration.FILE_COLLECTION_DIR);

            boolean fileExists = false;
            String existingFileName = null;

            if (dir.exists() && dir.isDirectory()) {
                String[] files = dir.list();
                if (files != null) {
                    for (String file : files) {
                        if (file.equals(formatingName)) {
                            fileExists = true;
                            existingFileName = file;
                            break;
                        }
                    }
                }
            }

            if (fileExists) {
                return existingFileName;
                /*
                System.out.println("""
                        File already exists. Continue work with file?

                        ✅YES
                        ❌NO""");

                String choice = sc.nextLine().trim().toLowerCase();
                switch (choice) {
                    case "yes": {
                        System.out.println("Ok, continue work!");
                        return existingFileName;
                    }
                    case "no": {
                        System.out.println("""
                                Ok, not continue work!\s
                                Try input again!
                                """);
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice,Try again.");
                        break;
                    }
                }
            }*/
            }
            return formatingName;
                /*
                System.out.println("""
                        ❌Error: File not found!
                        Do yo want to create new File?
                        
                        ✅YES
                        ❌NO
                        """);
                String choice = sc.nextLine().trim().toLowerCase();
                switch (choice) {
                    case "yes": {
                        System.out.println("Ok, created new file!");
                        return formatingName;
                    }
                    case "no": {
                        System.out.println("""
                                Ok, not create a new File!\s
                                """);
                        return null;

                    }
                    default: {
                        System.out.println("Invalid choice,Try again.");
                        break;
                    }

                }
            }*/

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

        if (fileName.endsWith(".csv")||fileName.endsWith(".CSV")) {
            return fileName;
        } else if (fileName.endsWith(".")) {
            return fileName + "csv";
        } else if (fileName.contains(".") && !fileName.endsWith(".csv")) {
            return fileName + ".csv";
        } else {
            return fileName + ".csv";
        }
    }

    public static String checkerString(String prompt, Scanner scanner,String field,Boolean digit) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if(hasDigit(input)>0 && !digit)
            {
                System.out.printf("❌ %s cannot has digit(s) ! Please try again.\n",field);
                continue;
            }
            if(digit && hasDigit(input)>1){
                System.out.printf("❌ %s cannot has more 2 digit ! Please try again.\n",field);
                continue;
            }

            String result = Validators.validateTitle(input);

            if (!"Unknown".equals(result)) {
                return result;
            }
            else{
                System.out.printf("❌ %s cannot be empty! Please try again.\n",field);
            }
            System.out.printf("❌ %s cannot be empty! Please try again.\n",field);
        }
    }
    public static String checkerString(String prompt, Scanner scanner, String field) {
        return checkerString(prompt, scanner, field, false);
    }


    private static int hasDigit(String input) {
        int count = 0;
        for(char c: input.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
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