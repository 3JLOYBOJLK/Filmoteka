package com._3JLOYBOJLK;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CSVManager csvManager = new CSVManager();
        List<Movie> movies = csvManager.loadMoviesFromCSV("movies.csv");

        for(Movie movie : movies){
            System.out.println(movie);
        }

        //вынести в отдельный метод
        System.out.println("Input nameOfFile, where you want save: ");
        int flag = 0;
        while(flag!=1){
            String fileForCSVSave = sc.nextLine().trim();
            int lengthFileName = fileForCSVSave.length();
           if(fileForCSVSave.toLowerCase().endsWith(".csv")){
               File file = new File(fileForCSVSave);
               flag = 1;
               break;
           }
           else if(fileForCSVSave.endsWith(".")){
               File file = new File(fileForCSVSave+"csv");
               flag = 1;
               break;
           }
           else if(fileForCSVSave.contains(".")&& !fileForCSVSave.endsWith(".")){
               File file = new File(fileForCSVSave+".csv");
               flag = 1;
               break;
           }
           else {
               File file = new File(fileForCSVSave+".csv");
               flag = 1;
               break;
           }




        }

        csvManager.saveMoviesToCSV(movies, fileForCSVSave);
        List<Movie> moviesFromCSV = csvManager.loadMoviesFromCSV(fileForCSVSave);

        for(Movie movie: moviesFromCSV){
            System.out.println(movie);
        }
    }
}