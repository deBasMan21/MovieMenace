package nl.avans.moviemenace.domain;

import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private String overview;
    private LocalDate release_date;
    private boolean adult;

    public Movie(int id, String title, String overview, LocalDate release_date, boolean adult) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.adult = adult;
    }
}
