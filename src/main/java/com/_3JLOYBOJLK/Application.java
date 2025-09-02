package com._3JLOYBOJLK;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Application {

    private final Scanner sc;
    private final MovieCollection collection;
    private MovieCollection collectionFromFile;
    private final Menu menu;


    public Application() {
        this.sc = new Scanner(System.in);
        this.collection = new MovieCollection();
        this.collection.loadFromDefaultFile("movies.csv");
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
            case 1 -> {
                showCollectionMoviesMenu();
                waitEnter(sc);
            }
            case 2 -> {
                addMovieMenu();
                waitEnter(sc);
            }

            case 3 -> {
                removeMovieMenu();
                waitEnter(sc);
            }
            case 4 -> {
                searchByDirectorMenu();
                waitEnter(sc);
            }
            case 5 -> {
                loadFromCSVFileMenu();
                waitEnter(sc);
            }
            case 6 -> {
                saveToCSVFileMenu();
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

    private void choicecollectionForSave(BiConsumer<MovieCollection,String > function1, BiConsumer<MovieCollection, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    String fileName = Validators.validateFile(sc,AppConfiguration.CREATE_FILE_MODE);
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
                    String fileName = Validators.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
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
    private void choicecollection(BiConsumer<MovieCollection,String > function1, BiConsumer<MovieCollection, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                        showCollectionMovies(collection);
                        function1.accept(collection,"movies.csv");

                }
                case 2 -> {
                    String fileName = Validators.validateFile(sc,AppConfiguration.CREATE_FILE_MODE);
                    if(fileName==null){
                        System.out.printf("❌Ok, GoodBye! \n");
                    }
                    else{
                        System.out.println(fileName);
                        collectionFromFile.loadFromFile(fileName);
                        showCollectionMovies(collectionFromFile);
                        function2.accept(collectionFromFile,fileName);
                    }
                }
                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    private void choicecollectionForRemoveAndSearch(BiConsumer<MovieCollection,String > function1, BiConsumer<MovieCollection, String> function2) {
        System.out.println("=== Choice Collection ===");
        System.out.println("1. Default collection (resources/movies.csv)\n" + "2. Collection from file (CreatablesFiles/)\n");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    function1.accept(collection,"movies.csv");
                }
                case 2 -> {
                    String fileName = Validators.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
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
                        showMoviesCurrentCollection(collectionFromFile.getMovies());
                    }
                }

                default -> System.out.println("❌Error: Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌Error: Please enter a valid number");
        }
    }

    private void showCollectionMoviesMenu() {
        choiceCollection(this::showCollectionMovies, this::showCollectionMoviesFromFile);
    }

    private void showCollectionMovies(MovieCollection collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection");
        } else {
            showMoviesCurrentCollection(collection.getMovies());
        }
    }

    private void showCollectionMoviesFromFile(MovieCollection collection, String directoryName) {
        String fileForLoaded = Validators.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
        if(fileForLoaded==null){
            return;
        }
        else{
            collection.loadFromFile(fileForLoaded);
            System.out.printf("=== %s ===\n",fileForLoaded);
            showCollectionMovies(collection);
        }
        collectionFromFile=new MovieCollection();

    }



    private void addMovieMenu() {
        choicecollection(this::addMoviesToCollection, this::addMoviesToCollection);
    }

    private void addMoviesToCollection(MovieCollection collection, String fileName) {
        System.out.printf( "===      %s       ===\n",fileName);
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
        choicecollectionForRemoveAndSearch(this::removeMovieFromCollection, this::removeMovieFromCollection);
    }

    private void removeMovieFromCollection(MovieCollection collection,String fileName) {
        if (collection == collectionFromFile) {
            collectionFromFile.loadFromFile(fileName);
        }
        if (collection.getMovies().isEmpty()) {
            System.out.println("❌Error: There are no movies in the collection for remove");
        }
        else {
            showMoviesCurrentCollection(collection.getMovies());
            System.out.printf(" ===     %s       ===\n",fileName);
            System.out.println("=== REMOVE MOVIE ===");
            String title = Validators.checkerString("Enter title: ", sc,"Title",AppConfiguration.ALLOW_DIGIT);

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

    private void searchByDirectorMenu() {
        choicecollectionForRemoveAndSearch(this::searchByDirector, this::searchByDirector);
    }

    private void searchByDirector(MovieCollection collection,String fileName) {
        if(collection==collectionFromFile){
            collectionFromFile.loadFromFile(fileName);
        }
        showMoviesCurrentCollection(collection.getMovies());
        System.out.printf(" ===         %s         ===\n",fileName);
        System.out.println("=== SEARCH BY DIRECTOR ===");
        String director = Validators.checkerString("Enter director: ", sc,"Director");

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

    private static void showMoviesCurrentCollection(List<Movie> collection) {
        for (Movie movie : collection) {
            System.out.println(movie);
        }
    }

    private void loadFromCSVFileMenu() {

        choiceCollection(this::loadCollectionFromCSVFile);
    }

    private void loadCollectionFromCSVFile(MovieCollection collection, String directoryName) {
        String fileName = Validators.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);

        collection.loadFromFile(fileName);
        int sizeCollection = collection.getMovies().size();

        if (sizeCollection > 0) {
            System.out.printf("✅ Movies successfully loaded from %s. Loaded %d movies.\n", fileName, sizeCollection);
        }
    }

    private void saveToCSVFileMenu() {
        choicecollectionForSave(this::saveProgramCollection,this::saveProgramCollection);
    }

    private void saveProgramCollection(MovieCollection collection, String fileName) {
        String filePath = (AppConfiguration.FILE_COLLECTION_DIR+fileName);
        String fileNameForSaving = "";
        if(collection==collectionFromFile){
            System.out.printf("When you want to save collection from file ' %s ' \n",fileName);
            fileNameForSaving = Validators.validateFile(sc,true);
            if(fileNameForSaving==null){
                System.out.println("Ok, GoodBye!");
                return;
            }
            File file = new File(AppConfiguration.FILE_COLLECTION_DIR+fileNameForSaving);
        }
        String newFileName = "";
        String newFilePath = collection==collectionFromFile?AppConfiguration.FILE_COLLECTION_DIR+fileNameForSaving:filePath;
        if (collection.saveToFile(newFilePath)) {
            newFileName= (collection==collectionFromFile)?fileNameForSaving:fileName;
            System.out.printf("✅ Movies successfully saved to ' %s '\n",newFileName);
        }
        else {
            System.out.printf("❌Error: movies can't be saved to %s\n", newFileName);
        }
        collectionFromFile=new MovieCollection();
    }
    private void deleteFileCollectionMenu(){
        String fileName = Validators.validateFile(sc,AppConfiguration.NOT_CREATE_FILE_MODE);
        if (fileName==null){
            return;
        }
        deleteFileCollection(fileName);

    }
    private void deleteFileCollection(String fileName) {
        File file = new File(AppConfiguration.FILE_COLLECTION_DIR+fileName);
        if(file.delete()){
            System.out.printf("✅File %s deleted successfully!\n", fileName);
        }
        else{
            System.out.println("❌File could not be deleted!");
        }
    }

    private void waitEnter(Scanner sc){
        System.out.println("Press Enter to continue...");
        String enter = sc.nextLine();
    }

}