package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.domain.Translation;

public class SQLMovieListDAO extends DatabaseConnection implements MovieListDAO {
    @Override
    public void createMovieList(String name, String desc, String email) {
        openConnection();
        try {
            String SQL = "INSERT INTO LIST(Name, Description, Email) VALUES('" +
                    name.replace("'", "''") + "', '" +
                    desc.replace("'", "''") + "', '" +
                    email + "')";

            executeSQLStatement(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void addMovieToList(int listID, int movieID) {
        openConnection();
        try {
            String SQL = "INSERT INTO ListContent(ListID, MovieID) VALUES(";
            SQL = SQL + listID + ", " + movieID + ")";

            executeSQLStatement(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MovieList getMovieList(int id) {
        openConnection();
        //Creates a  movielist object without any data
        MovieList movieList = null;
        try{
            //This string contains the sql query that selects the list with a specific id
            String SQL = "SELECT * FROM List WHERE ID = " + id;
            //Executes the query
            executeSQLSelectStatement(SQL);
            //Adds the data to the movielist object
            movieList = new MovieList(rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Email"));
            //Adds the movies that are in a list to the movielist by using the method: getMoviesForList(id)
            movieList.setMovies(getMoviesForList(id));
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //Returns the movielist with the films in it
        return movieList;
    }

    @Override
    public ArrayList<MovieList> getMovieListsForAccount(String email) {
        openConnection();
        //Creates an arraylist for the movielists
        ArrayList<MovieList> movieLists = new ArrayList<>();
        try{
            //This string contains the query to search all movielists from one account
            String SQL = "SELECT * FROM List WHERE Email = '" + email + "'";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //Creates a loop for all the objects that are found and creates the objects for them
            while(rs.next()){
                //movielist is created with all its data
                MovieList movieList = new MovieList(rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Email"));
                //the movielist including the movies is added to the arraylist
                movieLists.add(movieList);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //Returns the list with movielists
        return movieLists;
    }

    @Override
    public ArrayList<Movie> getMoviesForList(int listID){
        openConnection();
        //Creates an arraylist for all the movies that are in a specific list
        ArrayList<Movie> moviesInList = new ArrayList<>();
        try{
            //This string contains the query for searching movies from a specific list
            String SQLMovies = "SELECT * FROM Listcontent AS L INNER JOIN MOVIE AS M ON L.MovieID = M.Id WHERE ListID = " + listID;
            //this executes the query
            executeSQLSelectStatement(SQLMovies);
            //creates a loop for alle the found items for a sudden list.
            while(rs.next()){
                //creates movie items
                Movie movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"), rs.getString("Status") ,rs.getInt("Duration"),
                        rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
                //adds movie items to the list
                moviesInList.add(movie);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list with the movies for a specific movielist
        return moviesInList;
    }

    public HashMap<String, Translation> getTranslationsForMovie(int id){
        openConnection();
        HashMap<String, Translation> translations = new HashMap<>();
        try{
            String SQL = "SELECT * FROM Translations WHERE Id = " + id;

            executeSQLSelectStatement(SQL);
            while (rs.next()){
                Translation trans = new Translation(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"), rs.getString("URL"), rs.getString("URL"));
                translations.put(rs.getString("Language"), trans);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return translations;
    }

    public void deleteMovieFromList(int movieID, int listId){
        openConnection();
        try{
            String SQL = "DELETE FROM Listcontent WHERE MovieID = " + movieID + " AND ListID = " + listId;
            executeSQLStatement(SQL);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

}
