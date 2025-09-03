package com.filmoteka.service;

import com.filmoteka.model.Movie;
import com.filmoteka.model.Library;
import com.filmoteka.util.AppConfiguration;
import com.filmoteka.ui.Menu;
import com.filmoteka.service.ChoiceCollection;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Application {

    private final Scanner sc;
    private final Library collection;
    private Library collectionFromFile;
    private final Menu menu;
    private final ChoiceCollection choiceManager;
    private final LibraryService libraryService;


    public Application() {
        this.sc = new Scanner(System.in);
        this.collection = new Library();
        this.collection.loadFromDefaultFile("movies.csv");
        this.collectionFromFile = new Library();
        this.menu = new Menu();
        this.choiceManager = new ChoiceCollection(sc,collection,collectionFromFile);
        this.libraryService = new LibraryService(sc,collection,collectionFromFile);
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
            case 1 -> {
                showCollectionMoviesMenu();
                waitEnter(sc);
            }
            case 2 -> {
                addMovieMenu();
                collectionFromFile.clear();
                waitEnter(sc);
            }

            case 3 -> {
                removeMovieMenu();
                collectionFromFile.clear();
                waitEnter(sc);
            }
            case 4 -> {
                searchByDirectorMenu();
                collectionFromFile.clear();
                waitEnter(sc);
            }
            case 5 -> {
                loadFromCSVFileMenu();
                waitEnter(sc);
            }
            case 6 -> {
                saveToCSVFileMenu();
                collectionFromFile.clear();
                waitEnter(sc);
            }
            case 7->{
                deleteFileCollectionMenu();
                waitEnter(sc);
            }
            case 8 -> {
                System.out.println("\uD83D\uDC4B Thanks for work with collections!\nGood bye!");
                return false;
            }
            default -> System.out.println("❌Error: Invalid choice");
        }
        return true;
    }


    private void showCollectionMovies(Library collection) {
        libraryService.showCollectionMovies(collection);
    }
    private void showCollectionMoviesFromFile(Library collection, String directoryName) {
        libraryService.showCollectionMoviesFromFile(collectionFromFile, directoryName);
    }

    private void showCollectionMoviesMenu() {
        choiceManager.choiceCollection(this::showCollectionMovies, this::showCollectionMoviesFromFile);
    }


    private void addMoviesToCollection(Library collection,String fileName) {
        libraryService.addMoviesToCollection(collection,fileName);
    }

    private void addMovieMenu() {
        choiceManager.choicecollection(this::addMoviesToCollection, this::addMoviesToCollection);
    }


    private void removeMovieFromCollection(Library collection,String fileName){
        libraryService.removeMovieFromCollection(collection,fileName);
    }
    private void removeMovieMenu() {
        choiceManager.choicecollectionForRemoveAndSearch(this::removeMovieFromCollection, this::removeMovieFromCollection);
    }


    private void searchByDirector(Library collection,String fileName){
        libraryService.searchByDirector(collection,fileName);
    }
    private void searchByDirectorMenu() {
        choiceManager.choicecollectionForRemoveAndSearch(this::searchByDirector, this::searchByDirector);
    }


    private void loadCollectionFromCSVFile(Library collection, String directoryName) {
        libraryService.loadCollectionFromCSVFile(collection,directoryName);
    }
    private void loadFromCSVFileMenu() {
        choiceManager.choiceCollection(this::loadCollectionFromCSVFile);
    }


    private void saveProgramCollection(Library collection, String fileName){
        libraryService.showCollectionMovies(collection);
        libraryService.saveProgramCollection(collection,fileName);
    }
    private void saveToCSVFileMenu() {
        choiceManager.choicecollectionForSave(this::saveProgramCollection,this::saveProgramCollection);
    }


    private void deleteFileCollectionMenu(){
        System.out.println("=== Delete File Collection ===");

        LibraryService.listCSVFiles(AppConfiguration.FILE_COLLECTION_DIR);
        String fileName = ValidationService.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
        if (fileName==null){
            return;
        }
        libraryService.deleteFileCollection(fileName);

    }

    private void waitEnter(Scanner sc){
        System.out.println("Press Enter to continue...");
        String enter = sc.nextLine();
    }

}