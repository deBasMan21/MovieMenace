package nl.avans.moviemenace.dataLayer.IDAO;

import java.util.ArrayList;
import java.util.HashMap;

import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.domain.Translation;

public interface MovieListDAO {
    void createMovieList(String name, String desc, String email);
    void addMovieToList(int listID, int movieID);
    MovieList getMovieList(int id);
    ArrayList<MovieList> getMovieListsForAccount(String email);
    ArrayList<Movie> getMoviesForList(int listID);
    HashMap<String, Translation> getTranslationsForMovie(int id);
    void deleteMovieFromList(int movieId, int listId);
}
