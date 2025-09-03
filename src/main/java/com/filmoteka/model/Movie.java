package com.filmoteka.model;

import com.filmoteka.service.ValidationService;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String genre;
    private double rating;

    public Movie(String title, int year, String director) {

        this.title = ValidationService.validateTitle(title);

        this.year = ValidationService.validateYear(year);

        this.director = ValidationService.validateDirector(director);

        this.genre = "Unknown";
        this.rating = 0.0;
    }

    public Movie(String title, int year, String director, String genre, double rating) {

        this.title = ValidationService.validateTitle(title);

        this.year = ValidationService.validateYear(year);

        this.director = ValidationService.validateDirector(director);

        this.genre = ValidationService.validateGenre(genre);

        this.rating = ValidationService.validateRating(rating);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = ValidationService.validateTitle(title);
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = ValidationService.validateYear(year);
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = ValidationService.validateDirector(director);
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = ValidationService.validateGenre(genre);
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = ValidationService.validateRating(rating);
    }

    //do straight output
    public String toString() {
        return String.format(
                "┌───────────────────────────────────┐\n" +
                        "│ %-33s │\n" +
                        "├───────────────────────────────────┤\n" +
                        "│ %-33s │\n" +
                        "│ %-33s │\n" +
                        "│ %-33s │\n" +
                        "│ %-33s │\n" +
                        "└───────────────────────────────────┘",
                "🎬 " + ValidationService.validateTitle(title),
                "📅 " + ValidationService.validateYear(year),
                "🎥 " + ValidationService.validateDirector(director),
                "\uD83C\uDFF7\uFE0F " + ValidationService.validateGenre(genre),
                "⭐ " + (ValidationService.validateRating(rating)> 0 ?
                        String.format("%.1f/10", ValidationService.validateRating(rating)) : "N/A")
        );
    }



    public String toCSVString() {
        return String.format("%s;%d;%s;%s;%.1f",
                ValidationService.validateTitle(title),
                ValidationService.validateYear(year),
                ValidationService.validateDirector(director),
                ValidationService.validateGenre(genre),
                ValidationService.validateRating(rating)
        );
    }




}
