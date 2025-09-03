package com.filmoteka.service;

import com.filmoteka.model.Library;
import com.filmoteka.model.Movie;
import com.filmoteka.util.AppConfiguration;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class LibraryService {
    private final Scanner sc;
    private final Library collection;
    private  Library collectionFromFile;

    public LibraryService(Scanner sc, Library collection, Library collectionFromFile) {
        this.sc = sc;
        this.collection = collection;
        this.collectionFromFile = collectionFromFile;
    }
    public void showCollectionMovies(Library collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection");
        } else {
            showMoviesCurrentCollection(collection.getMovies());
        }
    }

    public void showCollectionMoviesFromFile(Library collection, String directoryName) {
        String fileForLoaded = ValidationService.validateFile(sc, AppConfiguration.NOT_CREATE_FILE_MODE);
        if(fileForLoaded==null){
            return;
        }
        else{
            collection.loadFromFile(fileForLoaded);
            System.out.printf("=== %s ===\n",fileForLoaded);
            showCollectionMovies(collection);
        }
        collectionFromFile=new Library();

    }

    public void addMoviesToCollection(Library collection, String fileName) {
        System.out.printf( "===      %s       ===\n",fileName);
        System.out.println("=== Add new movie ===");
        try {
            String title = ValidationService.checkerString("Enter title: ", sc,"Title",true);
            int year = ValidationService.checkerYear("Enter year: ", sc);
            String director = ValidationService.checkerString("Enter director: ", sc,"String");
            String genre = ValidationService.checkerString("Enter genre: ", sc,"Genre");
            double rating = ValidationService.checkerRating("Enter rating: ", sc);

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

    public void removeMovieFromCollection(Library collection, String fileName) {
        if (collection == collectionFromFile) {
            collectionFromFile.loadFromFile(fileName);
        }
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection for remove");
        }
        else {
            showMoviesCurrentCollection(collection.getMovies());
            System.out.printf(" ===     %s       ===\n",fileName);
            System.out.println("=== Remove movie ===");
            String title = ValidationService.checkerString("Enter title: ", sc,"Title",AppConfiguration.ALLOW_DIGIT);

            if (collection.removeMovie(title)) {
                if(collection==this.collection){
                    collection.saveToFile(AppConfiguration.CURRENT_COLLECTION_DIR+fileName);
                }
                else{
                    collection.saveToFile(AppConfiguration.FILE_COLLECTION_DIR+fileName);
                }
                System.out.println("✅ Movie remove successfully!");
            }
            else System.out.println("❌Remove failed.");
        }
    }

    public void searchByDirector(Library collection, String fileName) {
        if(collection==collectionFromFile){
            collectionFromFile.loadFromFile(fileName);
        }
        showMoviesCurrentCollection(collection.getMovies());
        System.out.printf(" ===         %s         ===\n",fileName);
        System.out.println("=== Search by director ===");
        String director = ValidationService.checkerString("Enter director: ", sc,"Director");

        List<Movie> searchedMovie = collection.searchByDirector(director);

        if (!searchedMovie.isEmpty()) {
            System.out.printf("✅ Found %d movies by director %s:\n", searchedMovie.size(), director);
            System.out.println("Show?(Yes/No)");
            String answer = sc.nextLine().toLowerCase();
            switch (answer) {
                case "yes":{
                    System.out.println("=== Searched movies ===");
                    showMoviesCurrentCollection(searchedMovie);
                }
                case "no":{
                    return;
                }
                default:
                    System.out.println("Goodbye!");

            }

        } else {
            System.out.printf("❌ Not found movies by director ' %s '\n", director);
        }
    }

    public static void showMoviesCurrentCollection(List<Movie> collection) {
        for (Movie movie : collection) {
            System.out.println(movie);
        }
    }

    public void loadCollectionFromCSVFile(Library collection, String directoryName) {
        String fileName = ValidationService.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);

        collection.loadFromFile(fileName);
        int sizeCollection = collection.getMovies().size();

        if (sizeCollection > 0) {
            System.out.printf("✅ Movies successfully loaded from %s. Loaded %d movies.\n", fileName, sizeCollection);
        }
    }

    public void saveProgramCollection(Library collection, String fileName) {
        try {
            String filePath;
            String finalFileName;
            if(collection==collectionFromFile){
                System.out.printf("Enter filename to save collection from file ' %s ' \n",fileName);
                String fileNameForSaving = ValidationService.validateFile(sc,AppConfiguration.ALLOW_DIGIT);

                if(fileNameForSaving==null){
                    System.out.println("❌ Save cancelled.");
                    return;
                }
                finalFileName = fileNameForSaving;
                filePath = AppConfiguration.FILE_COLLECTION_DIR + finalFileName;
            }
            else{
                finalFileName = fileName;
                filePath = AppConfiguration.CURRENT_COLLECTION_DIR + finalFileName;
            }

            if (collection.saveToFile(filePath)) {
                System.out.printf("✅ Movies successfully saved to ' %s '\n",finalFileName);
                if(collection==collectionFromFile){
                    collectionFromFile.loadFromFile(finalFileName);
                }
                else {
                    System.out.printf("❌Error: movies can't be saved to %s\n", finalFileName);
                }
            }
        } catch (Exception e) {
            System.out.println("❌Error: during save: " + e.getMessage());
        }
    }

    public void deleteFileCollection(String fileName) {
        File file = new File(AppConfiguration.FILE_COLLECTION_DIR+fileName);
        if(file.delete()){
            System.out.printf("✅File %s deleted successfully!\n", fileName);
        }
        else{
            System.out.println("❌File could not be deleted!");
        }
    }
    public static void listCSVFiles(String directoryPath){
        File directory = new File(directoryPath);
        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if(files!=null){
                System.out.println("📊 CSV Files in directory '" + directoryPath + "':");
               boolean foundCSVFile = false;
               for (File file : files) {
                   if (file.isFile()&& file.getName().endsWith(".csv")) {
                       System.out.println( file.getName() );
                       foundCSVFile = true;
                   }
               }
               System.out.println();
               if(!foundCSVFile){
                   System.out.println("❌ CSV Files not found");
               }
            }
            else{
                System.out.println("❌ Can't read this directory or directory empty");
            }
        }
        else{
            System.out.println("❌ Directory doesn't exist");
        }
    }


}
