package com._3JLOYBOJLK;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CSVManager csvManager = new CSVManager();

        // Проверяем существование файла перед загрузкой
        File inputFile = new File("movies.csv");
        if (!inputFile.exists()) {
            System.out.println("Файл movies.csv не найден в: " + inputFile.getAbsolutePath());
            System.out.println("Создаю новый список фильмов...");
        }

        List<Movie> movies = csvManager.loadMoviesFromCSV("movies.csv");

        // Вывод фильмов
        movies.forEach(System.out::println);

        // Сохранение
        System.out.println("Input nameOfFile, where you want save: ");
        String myFile = Validators.validateFile(sc);
        csvManager.saveMoviesToCSV(movies, myFile);

        // Проверка сохранения
        System.out.println("Проверка загрузки сохраненного файла:");
        List<Movie> loadedMovies = csvManager.loadMoviesFromCSV(myFile);
        loadedMovies.forEach(System.out::println);
    }
}