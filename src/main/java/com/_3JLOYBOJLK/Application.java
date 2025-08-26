package com._3JLOYBOJLK;

import java.awt.desktop.ScreenSleepEvent;
import java.sql.SQLOutput;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private final Scanner sc;
    private final MovieCollection collection;
    private final MovieCollection colectionFromFile;
    private final Menu menu;

    public Application() {
        this.sc = new Scanner(System.in);
        this.collection = new MovieCollection();
        this.colectionFromFile = new MovieCollection();
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
            case 1 -> showCollectionMovies();
            case 2 -> addMovie();
            case 3 -> removeMovie();
            case 4 -> searchByDirectorCurrentCollection();
            case 5 -> searchByDirectorFromFile();
            case 6 -> loadFromCSVFile();
            case 7 -> saveToCSVFile();
            case 8 -> { return false; }
            default -> System.out.println("❌Error: Invalid choice");
        }
        return true;
    }

    private void showCollectionMovies() {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Current collection\n"+"2. Collection from file\n");
        int choiceCollection = Integer.parseInt(sc.nextLine());
        switch (choiceCollection){
            case 1 -> showCurrentCollectionMovies();
            case 2 ->showCollectionFromFile();
            default -> System.out.printf("❌Error: Invalid Choice");
        }
    }

    private void showCurrentCollectionMovies() {
        if (collection.getMovies().isEmpty()){
            System.out.println("❌Error: There are no movies in the collection");
        }
        else{
            for (Movie movie : collection.getMovies()) {
                System.out.println(movie);
            }
        }
    }

    private void showCollectionFromFile(){
        String fileForLoaded =  Validators.validateFile(sc);
        colectionFromFile.loadFromFile("\\Filmoteka\\src\\main\\CreatablesFiles\\"+fileForLoaded);
        if(!colectionFromFile.getMovies().isEmpty()){
            for(Movie movie : colectionFromFile.getMovies()){
                System.out.println(movie);
            }
        }
        else System.out.printf("❌Error: There are no movies in the %s\n",fileForLoaded);
    }

    private void addMovie(){
        System.out.println("Choice collection");
        System.out.println("1. Current collection\n"+"2.Collection from file\n");
        int choiceCollection = Integer.parseInt(sc.nextLine());
        switch (choiceCollection){
            case 1 -> addMovieToCurrentCollection();
            case 2 -> addMovieToCollectionFromFile();
            default -> System.out.println("❌Error: Invalid Choice");
        }
    }

    private void addMovieToCurrentCollection() {
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
    private void addMovieToCollectionFromFile() {
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

            if (collectionFromFile.addMovie(newMovie)) {
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

    //check this
    private void  searchByDirectorCurrentCollection(){
       List<Movie> searchedMovie = new ArrayList<>();
        if(collection.getMovies().isEmpty()){
            System.out.println("❌Error: There are no movies in the collection for remove");
        }
        else{
            System.out.println("=== SEARCH BY DIRECTOR ===");
            String director = checkerString("Enter director: ", sc);
            searchedMovie = collection.searchByDirector(director);

            if(!searchedMovie.isEmpty()){
                MovieCollection.printSearchResult(searchedMovie,searchedMovie.size());
                for(Movie movie: searchedMovie){
                    System.out.println(movie);
                }
                }
            else{
                System.out.printf("❌ Not found movies by %s\n",director);
            }

        }
    }

    private void  searchByDirectorFromFile(){
        List<Movie> searchedMovie = new ArrayList<>();
        String fileForLoaded =  Validators.validateFile(sc);
        colectionFromFile.loadFromFile("\\Filmoteka\\src\\main\\CreatablesFiles\\"+fileForLoaded);


        if(colectionFromFile.getMovies().isEmpty()){
            System.out.println("❌Error: There are no movies in the collection for remove");
        }
        else{
            System.out.println("=== SEARCH BY DIRECTOR ===");
            String director = checkerString("Enter director: ", sc);
            searchedMovie = colectionFromFile.searchByDirector(director);

            if(!searchedMovie.isEmpty()){
                MovieCollection.printSearchResult(searchedMovie,searchedMovie.size());
                for(Movie movie: searchedMovie){
                    System.out.println(movie);
                }
            }
            else{
                System.out.printf("❌ Not found movies by %s\n",director);
            }

        }
    }

    private void loadFromCSVFile() {
        String fileName = Validators.validateFile(sc);
        List<Movie> currentCollectionCopy = collection.getMovies();
        collection.loadFromFile("\\Filmoteka\\src\\main\\resources\\"+fileName);
        if((!collection.getMovies().isEmpty()) && !(collection.getMovies().equals(currentCollectionCopy))) System.out.printf("✅ Movies successfully loaded from %s\n",fileName);
        else System.out.printf("❌Error: movies can't loaded from %s", fileName);
    }

    //not create newFiles and adding in this
    private void saveToCSVFile() {
        System.out.println("Choice collection");
        System.out.println("1. Current collection\n" + "2. Collection from File");
        int choiceCollection = Integer.parseInt(sc.nextLine());
        switch (choiceCollection){
            case 1-> saveProgramCollection();
            case 2-> saveCollectionFromFile();
            default: System.out.printf("❌Error: Invalid choice);
        }

    }

    private void saveProgramCollection(){
        String fileName = Validators.validateFile(sc);
        if(collection.saveToFile("\\Filmoteka\\src\\main\\resources\\"+fileName)) System.out.printf("✅ Movies successfully saved to %s\n",fileName);
        else System.out.printf("❌Error: movies can't saved to %s", fileName);
    }

    private void saveCollectionFromFile(){
        String fileName = Validators.validateFile(sc);
        if(colectionFromFile.saveToFile("\\Filmoteka\\src\\main\\SavedFiles\\"+fileName)) System.out.printf("✅ Movies successfully saved to %s\n",fileName);
        else System.out.printf("❌Error: movies can't saved to %s", fileName);
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
