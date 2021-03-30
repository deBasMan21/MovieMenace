package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.domain.Movie;

public class SQLMovieDAO extends DatabaseConnection implements MovieDAO {
    @Override
    public Movie getMovie(int id) {
        //Creates a movie object without any data in it
        Movie movie = null;
        try{
            //This string contains the select query for a movie
            String SQL = "SELECT * FROM Movie WHERE Id = " + id;
            //This executes the query
            executeSQLSelectStatement(SQL);
            //The selected movie will be filled in with data
            movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"),rs.getString("ReleaseDate"), rs.getBoolean("Adult"));
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //Returns the movie object created before
        return movie;
    }

    @Override
    public ArrayList<Movie> getMovieByTitle(String title) {
        //Creates an arraylist and movie object. In this arraylist all the movies with the same title will be stored.
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = null;
        try{
            //This string contains the query where multiple movies can come out
            String SQL = "SELECT * FROM Movie WHERE Title = '" + title + "'";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //This is a loop for all the items that are selected in wich this data is parsed to movie objects. These objects are put in the list created above/
            while (rs.next()) {
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"));
                movies.add(movie);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //This returns the list with movies
        return movies;
    }
}
