package com._3JLOYBOJLK;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    public static List<Movie> loadMoviesFromCSV(String fileName) {
        List<Movie> movies = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.err.println("Файл не найден: " + file.getAbsolutePath());
            return movies;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(";");
                if (data.length == 5) {
                    String title = data[0].trim();
                    int year = Integer.parseInt(data[1].trim());
                    String director = data[2].trim();
                    String genre = data[3].trim().equals("N/A") ? null : data[3].trim();
                    double rating = Double.parseDouble(data[4].trim());

                    movies.add(new Movie(title, year, director, genre, rating));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed to load movies from " + fileName, e);
        }
        return movies;
    }

    public static void saveMoviesToCSV(List<Movie> movies, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Title;Year;Director;Genre;Rating\n"); // Исправлен разделитель

            for (Movie movie : movies) {
                writer.write(movie.toCSVString());
                writer.newLine();
            }
        } catch (IOException e) {
            new File(fileName).delete();
            throw new RuntimeException("Failed to save movies to " + fileName, e);
        }
    }
}
