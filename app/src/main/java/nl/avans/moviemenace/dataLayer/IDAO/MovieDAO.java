package nl.avans.moviemenace.dataLayer.IDAO;

import java.util.ArrayList;

import nl.avans.moviemenace.domain.Movie;

public interface MovieDAO {
    Movie getMovie(int id);
    ArrayList<Movie> getMovieByTitle(String title);
    ArrayList<Movie> getAllMovies();
}
