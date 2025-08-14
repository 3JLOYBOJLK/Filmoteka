package com._3JLOYBOJLK;
import java.time.Year;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String genre;
    private double rating;

    public Movie(String title, int year, String director) {

        this.title = MovieValidator.validateTitle(title);

        this.year = MovieValidator.validateYear(year);

        this.director = MovieValidator.validateDirector(director);

        this.genre = "Unknown";
        this.rating = 0.0;
    }

    public Movie(String title, int year, String director, String genre, double rating) {

        this.title = MovieValidator.validateTitle(title);

        this.year = MovieValidator.validateYear(year);

        this.director = MovieValidator.validateDirector(director);

        this.genre = MovieValidator.validateGenre(genre);

        this.rating = MovieValidator.validateRating(rating);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = MovieValidator.validateTitle(title);
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = MovieValidator.validateYear(year);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = MovieValidator.validateDirector(director);
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = MovieValidator.validateGenre(genre);
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = MovieValidator.validateRating(rating);
    }


    public String toString() {
        return String.format(
                "┌───────────────────────────────────┐\n" +
                        "│ %-33s │\n" +
                        "├───────────────────────────────────┤\n" +
                        "│ %-33s │\n" +
                        "│ %-33s │\n" +
                        "│ %-33s │\n" +
                        "└───────────────────────────────────┘",
                "🎬 " + MovieValidator.validateTitle(title),
                "📅 " + MovieValidator.validateYear(year),
                "🎥 " + MovieValidator.validateDirector(director),
                ":D " + MovieValidator.validateGenre(genre),
                "⭐ " + (MovieValidator.validateRating(rating)> 0 ?
                        String.format("%.1f/10", MovieValidator.validateRating(rating)) : "N/A")
        );
    }



    public String toCSVString() {
        return String.format("%s;%d;%s;%s;%.1f",
                MovieValidator.validateTitle(title),
                MovieValidator.validateYear(year),
                MovieValidator.validateDirector(director),
                MovieValidator.validateGenre(genre),
                MovieValidator.validateRating(rating)
        );
    }




}
