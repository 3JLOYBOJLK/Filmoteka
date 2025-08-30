package com._3JLOYBOJLK;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String genre;
    private double rating;

    public Movie(String title, int year, String director) {

        this.title = Validators.validateTitle(title);

        this.year = Validators.validateYear(year);

        this.director = Validators.validateDirector(director);

        this.genre = "Unknown";
        this.rating = 0.0;
    }

    public Movie(String title, int year, String director, String genre, double rating) {

        this.title = Validators.validateTitle(title);

        this.year = Validators.validateYear(year);

        this.director = Validators.validateDirector(director);

        this.genre = Validators.validateGenre(genre);

        this.rating = Validators.validateRating(rating);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = Validators.validateTitle(title);
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = Validators.validateYear(year);
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = Validators.validateDirector(director);
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = Validators.validateGenre(genre);
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = Validators.validateRating(rating);
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
                "🎬 " + Validators.validateTitle(title),
                "📅 " + Validators.validateYear(year),
                "🎥 " + Validators.validateDirector(director),
                "\uD83C\uDFF7\uFE0F " + Validators.validateGenre(genre),
                "⭐ " + (Validators.validateRating(rating)> 0 ?
                        String.format("%.1f/10", Validators.validateRating(rating)) : "N/A")
        );
    }



    public String toCSVString() {
        return String.format("%s;%d;%s;%s;%.1f",
                Validators.validateTitle(title),
                Validators.validateYear(year),
                Validators.validateDirector(director),
                Validators.validateGenre(genre),
                Validators.validateRating(rating)
        );
    }




}
