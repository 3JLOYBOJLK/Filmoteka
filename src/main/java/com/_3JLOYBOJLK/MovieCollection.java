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

    public void loadFromFile(String filename) {
        this.movies = csvManager.loadMoviesFromCSV(filename);
    }

    //✅ Movies successfully saved to filename=== FILMOTEKA ===, but not create and saving to file
    public boolean saveToFile(String filename) {
        if(csvManager.saveMoviesToCSV(movies, filename)){
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
                    "' (" + newMovie.getYear() + ") already exists in collection");
            return false;
        }

        movies.add(newMovie);
        System.out.println("Movie '" + newMovie.getTitle() +
                "' (" + newMovie.getYear() + ") successfully added");
        return true;
    }

    private boolean isMovieExists(Movie movie) {
        for (Movie currentMovie : movies) {
            if (currentMovie.getTitle().equalsIgnoreCase(movie.getTitle()) &&
                    currentMovie.getYear() == movie.getYear() &&
                    currentMovie.getDirector().equalsIgnoreCase(movie.getDirector())) {
                return true;
            }
        }
        return false;
    }

    public boolean removeMovie(String title, int year) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("❌Error: Title cannot be null or empty");
        }

        if (year < 1930 || year > Year.now().getValue()) {
            throw new IllegalArgumentException("❌Error: Invalid year: " + year);
        }

        for (int i = 0; i < movies.size(); i++) {
            Movie current = movies.get(i);
            if (current.getTitle().equalsIgnoreCase(title.trim()) &&
                    current.getYear() == year) {
                movies.remove(i);
                System.out.println("Movie '" + title + "' (" + year + ") successfully removed");
                return true;
            }
        }

        System.out.println("❌Error: Movie '" + title + "' (" + year + ") not found");
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
            if(movie.getTitle().toLowerCase().contains(searchDirectorLower)){
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

