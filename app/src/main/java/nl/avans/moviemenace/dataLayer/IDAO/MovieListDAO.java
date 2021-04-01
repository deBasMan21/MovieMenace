package nl.avans.moviemenace.dataLayer.IDAO;

import java.util.ArrayList;

import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;

public interface MovieListDAO {
    void createMovieList(String name, String desc, String email);
    MovieList getMovieList(int id);
    ArrayList<MovieList> getMovieListsForAccount(String email);
    ArrayList<Movie> getMoviesForList(int listID);
}
