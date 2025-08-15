package com._3JLOYBOJLK;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    public static List<Movie> loadMoviesFromCSV(String fileName){
        List<Movie> takeMovies = new ArrayList<Movie>();
        try {
            InputStream giveAccess = CSVManager.class.getResourceAsStream("/"+ fileName);

            if (giveAccess == null) {
                System.out.println("File not found");
                return new ArrayList<>();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(giveAccess));

                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length > 0 && data.length <= 5) {

                        String title = data[0].trim();
                        int year = Integer.parseInt(data[1].trim());
                        String director = data[2].trim();
                        String genre = data[3].trim();
                        double rating = Double.parseDouble(data[4].trim());

                        takeMovies.add(new Movie(title, year, director, genre, rating));
                    }
                }
                br.close();
            } catch (IOException | NumberFormatException e) {
                throw new RuntimeException("Failed to load movies from %s" + fileName, e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return takeMovies;
        }

        public static void saveMoviesToCSV(List<Movie> movies, String fileName){
            try{
                FileWriter file = new FileWriter(fileName);
                while(movies!=null){
                    file.write(movies.toString()+";");
                }
            }
            catch (IOException e){
                throw new RuntimeException("Failed to save movie to %s" + fileName, e);
            }



        }
}
