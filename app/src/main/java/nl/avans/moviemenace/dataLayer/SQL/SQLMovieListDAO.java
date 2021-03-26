package nl.avans.moviemenace.dataLayer.SQL;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.domain.MovieList;

public class SQLMovieListDAO implements MovieListDAO {
    @Override
    public MovieList getMovieList(int id) {
        return null;
    }

    @Override
    public MovieList getMovieListByName(String name) {
        return null;
    }

    @Override
    public ArrayList<MovieList> getMovieListsForAccount(String email) {
        return null;
    }
}
