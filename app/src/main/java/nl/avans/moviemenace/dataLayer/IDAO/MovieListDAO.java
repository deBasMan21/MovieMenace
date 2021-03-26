package nl.avans.moviemenace.dataLayer.IDAO;

import java.util.ArrayList;

import nl.avans.moviemenace.domain.MovieList;

public interface MovieListDAO {
    MovieList getMovieList(int id);
    MovieList getMovieListByName(String name);
    ArrayList<MovieList> getMovieListsForAccount(String email);
}
