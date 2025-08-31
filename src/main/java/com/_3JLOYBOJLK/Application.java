package com._3JLOYBOJLK;

import java.awt.desktop.ScreenSleepEvent;
import java.io.File;
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
    private void choiceCollection(BiConsumer<MovieCollection, String> function1, BiConsumer<MovieCollection, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    String fileName = Validators.validateFile(sc);
                    function1.accept(collection, fileName);
                }
                case 2 -> {
                    String fileName = Validators.validateFile(sc);
                    collectionFromFile.loadFromFile(AppConfiguration.FILE_COLLECTION_DIR+fileName);
                    function2.accept(collectionFromFile, fileName);
                }
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    private void choiceCollection(BiConsumer<MovieCollection,String> function1) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> function1.accept(collectionFromFile,AppConfiguration.FILE_COLLECTION_DIR);
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
                case 2 -> function2.accept(collectionFromFile, AppConfiguration.FILE_COLLECTION_DIR);
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
            showMoviesCurrentCollection(collection.getMovies());
        }
    }

    private void showCollectionMoviesFromFile(MovieCollection collection, String directoryName) {
        String fileForLoaded = Validators.validateFile(sc);
        String filePath = buildFilePath(directoryName, fileForLoaded);

        collection.loadFromFile(filePath);
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection");
        } else {
            showMoviesCurrentCollection(collection.getMovies());
        }
    }



    private void addMovieMenu() {
        choiceCollection(this::addMoviesToCollection, this::addMoviesToCollection);
    }

    private void addMoviesToCollection(MovieCollection collection, String fileName) {
        System.out.println("=== ADD NEW MOVIE ===");
        try {
            String title = Validators.checkerString("Enter title: ", sc,"Title",true);
            int year = Validators.checkerYear("Enter year: ", sc);
            String director = Validators.checkerString("Enter director: ", sc,"String");
            String genre = Validators.checkerString("Enter genre: ", sc,"Genre");
            double rating = Validators.checkerRating("Enter rating: ", sc);

            Movie newMovie = new Movie(title, year, director,
                    "Unknown".equals(genre) ? null : genre,
                    rating);

            if (collection.addMovie(newMovie)) {

                String filePath;
                if (collection == this.collection) {
                    filePath = AppConfiguration.CURRENT_COLLECTION_DIR + fileName;
                } else {
                    filePath = AppConfiguration.FILE_COLLECTION_DIR + fileName;
                }

                if (collection.saveToFile(filePath)) {
                    System.out.println("✅ Changes successfully saved to file: " + fileName);
                    File file = new File(filePath);
                    System.out.println(file.getAbsolutePath());

                } else {
                    System.out.println("⚠️  Movie added but failed to save to file!");
                }
            }
        } catch (Exception e) {
            System.out.println("❌Error: Failed to add movie: " + e.getMessage());
        }
    }


    private void removeMovieMenu() {
        choiceCollection(this::removeMovieFromCollection, this::removeMovieFromCollection);
    }

    private void removeMovieFromCollection(MovieCollection collection,String fileName) {
        if (collection == collectionFromFile) {
            String filePath = buildFilePath(AppConfiguration.FILE_COLLECTION_DIR, fileName);
            collectionFromFile.loadFromFile(filePath);
        }
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection for remove");
        } else {
            showMoviesCurrentCollection(collection.getMovies());
            System.out.println("=== REMOVE MOVIE ===");
            String title = Validators.checkerString("Enter title: ", sc,"Title",true);
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

    private void searchByDirector(MovieCollection collection,String fileName) {
        if(collection==collectionFromFile){
            String filePath = buildFilePath(AppConfiguration.FILE_COLLECTION_DIR, fileName);
            collectionFromFile.loadFromFile(filePath);
        }
        showMoviesCurrentCollection(collection.getMovies());
        System.out.println("=== SEARCH BY DIRECTOR ===");
        String director = Validators.checkerString("Enter director: ", sc,"Director");

        List<Movie> searchedMovie = collection.searchByDirector(director);

        if (!searchedMovie.isEmpty()) {
            System.out.printf("✅ Found %d movies by director %s:\n", searchedMovie.size(), director);
            System.out.println("Show?(Y/N)");
            String answer = sc.nextLine();
            switch (answer) {
                case "Y":showMoviesCurrentCollection(searchedMovie);
                case "N":{
                    return;
                }
                default:
                    System.out.println("Goodbye!");

            }

        } else {
            System.out.printf("❌ Not found movies by director %s\n", director);
        }
    }

    private static void showMoviesCurrentCollection(List<Movie> collection) {
        for (Movie movie : collection) {
            System.out.println(movie);
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

    private void saveProgramCollection(MovieCollection collection, String fileName) {

        String filePath = buildFilePath(AppConfiguration.FILE_COLLECTION_DIR, fileName);

        if (collection.saveToFile(filePath)) {
            System.out.printf("✅ Movies successfully saved to %s\n", fileName);
        } else {
            System.out.printf("❌Error: movies can't be saved to %s\n", fileName);
        }
    }

    private String buildFilePath(String directoryName, String fileName) {
        return directoryName + "/" + fileName;
    }
}