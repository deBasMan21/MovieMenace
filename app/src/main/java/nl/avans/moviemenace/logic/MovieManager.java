package nl.avans.moviemenace.logic;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.rooms.Entities.MovieEntity;
import nl.avans.moviemenace.domain.Movie;

// Class for interacting between UI and API/Datalayer.
public class MovieManager {

    private MovieDAO movieDAO;

    public MovieManager(DAOFactory factory){
        this.movieDAO = factory.createMovieDAO();
    }

    public ArrayList<Movie> getAllMovies(MovieEntityManager mem){
        DatabaseConnection db = new DatabaseConnection();
        db.openConnection();
        if(db.connectionIsOpen()){
            return movieDAO.getAllMovies();
        } else {
            return mem.getAllMovies();
        }
    }

    public ArrayList<Movie> getTop10Movies(MovieEntityManager mem){
        DatabaseConnection db = new DatabaseConnection();
        db.openConnection();
        if(db.connectionIsOpen()){
            return movieDAO.getTop10Movies();
        } else {
            return mem.getTop10Movies();
        }
    }

    public Movie getMovie(MovieEntityManager mem, int id){
        DatabaseConnection db = new DatabaseConnection();
        db.openConnection();
        if(db.connectionIsOpen()){
            return movieDAO.getMovie(id);
        } else {
            return mem.getMovie(id);
        }
    }

    public Movie getRandomMovie(MovieEntityManager mem){
        DatabaseConnection db = new DatabaseConnection();
        db.openConnection();
        if(db.connectionIsOpen()){
            return movieDAO.getRandomMovie();
        } else {
            return mem.getMovie(671);
        }
    }

    public static int calculateColumns(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

}
