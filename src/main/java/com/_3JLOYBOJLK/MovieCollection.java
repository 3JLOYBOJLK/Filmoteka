package com._3JLOYBOJLK;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class MovieCollection {
    private List<Movie> movies;
    private CSVManager csvManager;

    public MovieCollection() {
        this.movies = new ArrayList<>();
        this.csvManager = new CSVManager();
    }
    public List<Movie> getMovies() {
        return Collections.unmodifiableList(new ArrayList<>(movies));
    }

    public void loadFromDefaultFile(String filename) {
        this.movies = csvManager.loadMoviesFromCSV(AppConfiguration.CURRENT_COLLECTION_DIR,filename);
    }
    public void loadFromFile(String filename) {
        this.movies = csvManager.loadMoviesFromCSV(AppConfiguration.FILE_COLLECTION_DIR,filename);
    }

    //✅ Movies successfully saved to filename=== FILMOTEKA ===, but not create and saving to file
    public boolean saveToFile(String filePath) {
        if(csvManager.saveMoviesToCSV(movies, filePath) == true){
            return true;
        }
        return false;
    }
    public boolean addMovie(Movie newMovie) {
        if (newMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }

        if (isMovieExists(newMovie)) {
            System.out.println("❌Error: Movie '" + newMovie.getTitle() +
                    "' already exists in collection");
            return false;
        }

        movies.add(newMovie);
        System.out.println("Movie '" + newMovie.getTitle() +
                "' (" + newMovie.getYear() + ") successfully added");
        return true;
    }

    private boolean isMovieExists(Movie movie) {
        for (Movie currentMovie : movies) {
            if (currentMovie.getTitle().equalsIgnoreCase(movie.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public boolean removeMovie(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("❌Error: Title cannot be null or empty");
        }


        for (int i = 0; i < movies.size(); i++) {
            Movie current = movies.get(i);
            if (current.getTitle().equalsIgnoreCase(title.trim())) {
                movies.remove(i);
                System.out.println("Movie '" + title + "' successfully removed");
                return true;
            }
        }

        System.out.println("❌Error: Movie '" + title + "' not found");
        return false;
    }

    public List<Movie> searchByDirector(String director) {

        if(director == null){
            throw new IllegalArgumentException("❌Error: director cannot be null");
        }
        String searchDirector = director.trim();
        if(searchDirector.isEmpty()){
            throw new IllegalArgumentException("❌Error: director cannot be empty");
        }

        List<Movie> searchedMovies = new ArrayList<>();
        String searchDirectorLower = searchDirector.toLowerCase();
        for (Movie movie : movies) {
            if(movie.getDirector().toLowerCase().equals(searchDirectorLower)){
                searchedMovies.add(movie);
            }
        }

        return searchedMovies;

    }
    public static void printSearchResult(List<Movie> movies,int count){
        System.out.println("Find film by '" + movies.get(0).getDirector()+ "' : "+ count);

        if(count!=0) {
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }
    }
}

