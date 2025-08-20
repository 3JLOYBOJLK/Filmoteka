package com._3JLOYBOJLK;

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
            default -> System.out.println("Invalid choice");
        }
        return true;
    }
    private void showAllMovies() {
        if (collection.getMovies().isEmpty()){
            System.out.println("There are no movies in the collection");
        }
        else{
            for (Movie movie : collection.getMovies()) {
                System.out.println(movie);
            }
        }
    }

    private void addMovie() {

    }

    private void removeMovie() {

    }
    private void searchByDirector(){

    }
    private void loadFromCSVFile() {

    }
    private void saveToCSVFile() {

    }
}
