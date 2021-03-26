package nl.avans.moviemenace.domain;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private int id;
    private String name;
    private String description;
    private String email;
    private List<Movie> movies;

    public MovieList(int id, String name, String description, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.movies = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
