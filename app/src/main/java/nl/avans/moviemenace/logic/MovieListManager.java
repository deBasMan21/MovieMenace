package nl.avans.moviemenace.logic;

import java.util.ArrayList;
import java.util.HashMap;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.domain.Translation;

public class MovieListManager {

    private MovieListDAO movieListDAO;

    public MovieListManager(DAOFactory daoFactory) {
        this.movieListDAO = daoFactory.createMovieListDAO();
    }

    public void createList(String name, String desc, String email) {
        movieListDAO.createMovieList(name, desc, email);
    }

    public void addMovieToList(int listID, int movieID) {
        movieListDAO.addMovieToList(listID, movieID);
    }

    public void deleteMovieFromList(int listID, int movieID) {
        movieListDAO.deleteMovieFromList(listID, movieID);
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

    public HashMap<String, Translation> getTranslationsForMovie(int id) {
        return movieListDAO.getTranslationsForMovie(id);
    }
}
