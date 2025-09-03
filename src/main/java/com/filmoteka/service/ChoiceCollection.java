package com.filmoteka.service;

import com.filmoteka.model.Library;
import com.filmoteka.util.AppConfiguration;
import com.filmoteka.service.ValidationService;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChoiceCollection {
    private final Scanner sc;
    private final Library collection;
    private  Library collectionFromFile;
    private final LibraryService libraryService;


    public ChoiceCollection(Scanner sc, Library collection, Library collectionFromFile) {
        this.sc = sc;
        this.collection = collection;
        this.collectionFromFile = collectionFromFile;
        this.libraryService = new LibraryService(sc,collection,collectionFromFile);
    }
    public void choicecollectionForSave(BiConsumer<Library,String > function1, BiConsumer<Library, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    String fileName = ValidationService.validateFile(sc, AppConfiguration.CREATE_FILE_MODE);
                    if(fileName==null){
                        System.out.printf("❌Can't work with file %s\n",AppConfiguration.FILE_COLLECTION_DIR+fileName);
                    }
                    else{
                        System.out.println(fileName);
                        collection.loadFromDefaultFile("movies.csv");
                        function1.accept(collection,fileName);
                    }

                }
                case 2 -> {
                    String fileName = ValidationService.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
                    if(fileName==null){
                        System.out.printf("❌Can't work with file %s\n",AppConfiguration.FILE_COLLECTION_DIR+fileName);
                    }
                    else{
                        System.out.println(fileName);
                        collectionFromFile.loadFromFile(fileName);
                        function2.accept(collectionFromFile,fileName);
                    }
                }
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }
    public void choicecollection(BiConsumer<Library,String > function1, BiConsumer<Library, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    libraryService.showCollectionMovies(collection);
                    function1.accept(collection,"movies.csv");

                }
                case 2 -> {
                    String fileName = ValidationService.validateFile(sc,AppConfiguration.CREATE_FILE_MODE);
                    if(fileName==null){
                        System.out.printf("❌Ok, GoodBye! \n");
                    }
                    else{
                        System.out.println(fileName);
                        collectionFromFile.loadFromFile(fileName);
                        libraryService.showCollectionMovies(collectionFromFile);
                        function2.accept(collectionFromFile,fileName);
                    }
                }
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    public void choicecollectionForRemoveAndSearch(BiConsumer<Library,String > function1, BiConsumer<Library, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    function1.accept(collection,"movies.csv");
                }
                case 2 -> {
                    String fileName = ValidationService.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
                    if(fileName==null){
                        System.out.printf("❌Can't work with file %s\n",AppConfiguration.FILE_COLLECTION_DIR+fileName);
                    }
                    else{
                        System.out.println(fileName);
                        collectionFromFile.loadFromFile(fileName);
                        function2.accept(collectionFromFile,fileName);
                    }
                }
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    public void choiceCollection(BiConsumer<Library,String> function1) {
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

    public void choiceCollection(Consumer<Library> function1, BiConsumer<Library, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection\n" + "2. Collection from file\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.printf("=== %s ===\n",AppConfiguration.defaultName);
                    function1.accept(collection);
                }
                case 2 -> {
                    if(collectionFromFile.getMovies().isEmpty())
                    {
                        function2.accept(collectionFromFile, AppConfiguration.FILE_COLLECTION_DIR);
                    }
                    else{
                        LibraryService.showMoviesCurrentCollection(collectionFromFile.getMovies());
                    }
                }

                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }
}
