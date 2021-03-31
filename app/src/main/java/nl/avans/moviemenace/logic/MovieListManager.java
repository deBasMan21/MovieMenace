package nl.avans.moviemenace.logic;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;

public class MovieListManager {

    private MovieListDAO movieListDAO;

    public MovieListManager(DAOFactory daoFactory) {
        this.movieListDAO = daoFactory.createMovieListDAO();
    }

    public MovieList getMovieList(int id) {
        return movieListDAO.getMovieList(id);
    }

    public ArrayList<MovieList> getMovieListsForAccount(String email) {
        return movieListDAO.getMovieListsForAccount(email);
    }

    public ArrayList<Movie> getMoviesForList(int listID) {
        return movieListDAO.getMoviesForList(listID);
    }
}
