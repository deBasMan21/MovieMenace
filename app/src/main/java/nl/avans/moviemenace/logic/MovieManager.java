package nl.avans.moviemenace.logic;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.domain.Movie;

// Class for interacting between UI and API/Datalayer.
public class MovieManager {

    private MovieDAO movieDAO;

    public MovieManager(DAOFactory factory){
        this.movieDAO = factory.createMovieDAO();
    }

    public ArrayList<Movie> getAllMovies(){
        return movieDAO.getAllMovies();
    }





}
