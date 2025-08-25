package com._3JLOYBOJLK;

import java.time.Year;
import java.util.Scanner;

public class Application {

    private final Scanner sc;
    private final MovieCollection collection;
    private final Menu menu;

    public Application() {
        this.sc = new Scanner(System.in);
        this.collection = new MovieCollection();
        this.menu = new Menu();
    }

    public void run() {
        boolean running = true;
        while (running) {
            menu.show();
            int getChoice = menu.getChoice(sc);
            running = processingChoice(getChoice);
        }
    }

    private boolean processingChoice(int choice) {
        switch (choice) {
            case 1 -> showAllMovies();
            case 2 -> addMovie();
            case 3 -> removeMovie();
            case 4 -> searchByDirector();
            case 5 -> loadFromCSVFile();
            case 6 -> saveToCSVFile();
            case 7 -> { return false; }
            default -> System.out.println("❌Error: Invalid choice");
        }
        return true;
    }
    private void showAllMovies() {
        if (collection.getMovies().isEmpty()){
            System.out.println("❌Error: There are no movies in the collection");
        }
        else{
            for (Movie movie : collection.getMovies()) {
                System.out.println(movie);
            }
        }
    }

    private void addMovie() {
        System.out.println("=== ADD NEW MOVIE ===");
        try {
            String title = checkerString("Enter title: ", sc);
            int year = checkerYear("Enter year: ", sc);
            String director = checkerString("Enter director: ", sc);
            String genre = checkerString("Enter genre: ", sc);
            double rating = checkerRating("Enter rating: ", sc);

            Movie newMovie = new Movie(title, year, director,
                    "Unknown".equals(genre) ? null : genre,
                    rating);

            if (collection.addMovie(newMovie)) {
                System.out.println("✅ Movie added successfully!");
            }
        } catch (Exception e) {
            System.out.println("❌Error: Failed to add movie: " + e.getMessage());
        }
    }




    private void removeMovie() {
        if(collection.getMovies().isEmpty()){
            System.out.println("❌Error: There are no movies in the collection for remove");
        }
        else{
            String title;
            int year;
            while(true) {
                System.out.println("=== REMOVE MOVIE ===");
                title = checkerString("Enter title: ", sc);
                year = checkerYear("Enter year: ", sc);
                break;
            }
            if (collection.removeMovie(title, year))  System.out.println("✅ Movie remove successfully!");
            else System.out.println("❌Error: Movie not found");

        }

    }
    private void searchByDirector(){

    }
    private void loadFromCSVFile() {

    }
    private void saveToCSVFile() {

    }


    private String checkerString(String prompt, Scanner scanner) {
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

    private boolean hasDigit(String input) {
        for(char c: input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private int checkerYear(String prompt, Scanner scanner) {
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

    private double checkerRating(String prompt, Scanner scanner) {
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
