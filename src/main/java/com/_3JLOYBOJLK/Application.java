package com._3JLOYBOJLK;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Application {

    private final Scanner sc;
    private final MovieCollection collection;
    private final MovieCollection collectionFromFile;
    private final Menu menu;


    public Application() {
        this.sc = new Scanner(System.in);
        this.collection = new MovieCollection();
        this.collection.loadFromFile(AppConfiguration.defaultPath);
        this.collectionFromFile = new MovieCollection();
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
            case 1 -> showCollectionMoviesMenu();
            case 2 -> addMovieMenu();
            case 3 -> removeMovieMenu();
            case 4 -> searchByDirectorMenu();
            case 5 -> loadFromCSVFileMenu();
            case 6 -> saveToCSVFileMenu();
            case 7 -> { return false; }
            default -> System.out.println("❌Error: Invalid choice");
        }
        return true;
    }
    private void choiceCollection(Consumer<MovieCollection> function1,Consumer<MovieCollection> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection\n" + "2. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> function1.accept(collection);
                case 2 -> function2.accept(collectionFromFile);
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }
    private void choiceCollection(BiConsumer<MovieCollection,String> function1,BiConsumer<MovieCollection,String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection\n" + "2. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> function1.accept(collection,"resources\\movies.csv");
                case 2 -> function2.accept(collectionFromFile,"CreatablesFiles");
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }
    private void choiceCollection(BiConsumer<MovieCollection,String> function1) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection\n" + "2. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> function1.accept(collectionFromFile,"CreatablesFile");
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }
    private void choiceCollection(Consumer<MovieCollection> function1, BiConsumer<MovieCollection, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection\n" + "2. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> function1.accept(collection);
                case 2 -> function2.accept(collectionFromFile, "CreatablesFiles");
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    private void showCollectionMoviesMenu() {
        choiceCollection(this::showDefaultCollectionMovies, this::showCollectionMoviesFromFile);
    }

    private void showDefaultCollectionMovies(MovieCollection collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection");
        } else {
            for (Movie movie : collection.getMovies()) {
                System.out.println(movie);
            }
        }
    }

    private void showCollectionMoviesFromFile(MovieCollection collection, String directoryName) {
        String fileForLoaded = Validators.validateFile(sc);
        String filePath = buildFilePath(directoryName, fileForLoaded);

        collection.loadFromFile(filePath);
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection");
        } else {
            for (Movie movie : collection.getMovies()) {
                System.out.println(movie);
            }
        }
    }

    private void addMovieMenu() {
        choiceCollection(this::addMoviesToCollection, this::addMoviesToCollection);
    }

    private void addMoviesToCollection(MovieCollection collection,String directoryName) {
        System.out.println("=== ADD NEW MOVIE ===");
        try {
            String title = Validators.checkerString("Enter title: ", sc);
            int year = Validators.checkerYear("Enter year: ", sc);
            String director = Validators.checkerString("Enter director: ", sc);
            String genre = Validators.checkerString("Enter genre: ", sc);
            double rating = Validators.checkerRating("Enter rating: ", sc);

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


    private void removeMovieMenu() {
        choiceCollection(this::removeMovieFromCollection, this::removeMovieFromCollection);
    }

    private void removeMovieFromCollection(MovieCollection collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection for remove");
        } else {
            System.out.println("=== REMOVE MOVIE ===");
            String title = Validators.checkerString("Enter title: ", sc);
            int year = Validators.checkerYear("Enter year: ", sc);

            if (collection.removeMovie(title, year)) {
                System.out.println("✅ Movie remove successfully!");
            } else {
                System.out.println("❌Error: Movie not found");
            }
        }
    }

    private void searchByDirectorMenu() {
        choiceCollection(this::searchByDirector, this::searchByDirector);
    }

    private void searchByDirector(MovieCollection collection) {
        System.out.println("=== SEARCH BY DIRECTOR ===");
        String director = Validators.checkerString("Enter director: ", sc);

        List<Movie> searchedMovie = collection.searchByDirector(director);

        if (!searchedMovie.isEmpty()) {
            System.out.printf("✅ Found %d movies by director %s:\n", searchedMovie.size(), director);
            for (Movie movie : searchedMovie) {
                System.out.println(movie);
            }
        } else {
            System.out.printf("❌ Not found movies by director %s\n", director);
        }
    }

    private void loadFromCSVFileMenu() {
        choiceCollection(this::loadCollectionFromCSVFile);
    }

    private void loadCollectionFromCSVFile(MovieCollection collection, String directoryName) {
        String fileName = Validators.validateFile(sc);
        String filePath = buildFilePath(directoryName, fileName);

        int previousSize = collection.getMovies().size();
        collection.loadFromFile(filePath);
        int newSize = collection.getMovies().size();

        if (newSize > previousSize) {
            System.out.printf("✅ Movies successfully loaded from %s. Loaded %d movies.\n", fileName, newSize - previousSize);
        } else {
            System.out.printf("❌Error: movies can't be loaded from %s or no new movies found\n", fileName);
        }
    }

    private void saveToCSVFileMenu() {
        choiceCollection(this::saveProgramCollection,this::saveProgramCollection);
    }

    private void saveProgramCollection(MovieCollection collection, String directoryName) {
        String fileName = Validators.validateFile(sc);
        String filePath = buildFilePath(directoryName, fileName);

        if (collection.saveToFile(filePath)) {
            System.out.printf("✅ Movies successfully saved to %s\n", fileName);
        } else {
            System.out.printf("❌Error: movies can't be saved to %s\n", fileName);
        }
    }

    private String buildFilePath(String directoryName, String fileName) {
        return "src/main/" + directoryName + "/" + fileName;
    }
}