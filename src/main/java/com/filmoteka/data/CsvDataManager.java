package com.filmoteka.data;

import com.filmoteka.model.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDataManager {

    public static List<Movie> loadMoviesFromCSV(String directoryName, String fileName){
        List<Movie> movies = new ArrayList<>();

        File file = creatingNewFile(directoryName+fileName);

        if (!file.exists()) {
            System.err.println("❌Error: File not found: " + file.getAbsolutePath());
            return null;
        }
        else{
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

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
                        double rating = Double.parseDouble(data[4].trim().replace(",","."));

                        movies.add(new Movie(title, year, director, genre, rating));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                throw new RuntimeException("❌Error: Failed to load movies from " + fileName, e);
            }
            return movies;
        }
    }

    private static File creatingNewFile(String filePath) {
        File file = new File(filePath);
        if(file.exists()){
            return file;
        }
        boolean fileCreated;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }


    public static boolean saveMoviesToCSV(List<Movie> movies, String filePath) {
        File file = creatingNewFile(filePath);
        if(!file.exists()) {
            System.out.println("❌Error:File can't created, failed to save movies.");
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int count= 0;
            for (Movie movie : movies) {
                writer.write(movie.toCSVString());
                writer.newLine();
                count++;
            }
            writer.flush();
            if (count>0) return true;
            return false;
        }
        catch (IOException e) {
            new File(filePath).delete();
            throw new RuntimeException("❌Error: Failed to save movies to " + filePath, e);
        }

    }

}
