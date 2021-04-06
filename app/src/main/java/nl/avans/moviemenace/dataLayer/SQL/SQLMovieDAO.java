package nl.avans.moviemenace.dataLayer.SQL;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Translation;

public class SQLMovieDAO extends DatabaseConnection implements MovieDAO {
    @Override
    public Movie getMovie(int id) {
        openConnection();
        //Creates a movie object without any data in it
        Movie movie = null;
        try{
            //This string contains the select query for a movie
            String SQL = "SELECT * FROM Movie WHERE Id = " + id;
            //This executes the query
            executeSQLSelectStatement(SQL);
            //The selected movie will be filled in with data
            while(rs.next()){
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"),rs.getString("ReleaseDate"), rs.getBoolean("Adult"), rs.getString("Status"), rs.getInt("Duration"), rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
                movie.setTranslations(getTranslationsForMovie(id));
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //Returns the movie object created before
        return movie;
    }

    @Override
    public ArrayList<Movie> getMovieByTitle(String title) {
        openConnection();
        //Creates an arraylist and movie object. In this arraylist all the movies with the same title will be stored.
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = null;
        try{
            //This string contains the query where multiple movies can come out
            String SQL = "SELECT * FROM Movie WHERE Title LIKE '%" + title + "%'";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //This is a loop for all the items that are selected in wich this data is parsed to movie objects. These objects are put in the list created above/
            while (rs.next()) {
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"), rs.getString("Status"),
                        rs.getInt("Duration"), rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
                movie.setTranslations(getTranslationsForMovie(rs.getInt("Id")));
                movies.add(movie);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //This returns the list with movies
        return movies;
    }

    @Override
    public ArrayList<Movie> getAllMovies() {
        openConnection();
        //Creates an arraylist and movie object. In this arraylist all the movies with the same title will be stored.
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = null;
        try{
            //This string contains the query where multiple movies can come out
            String SQL = "SELECT * FROM Movie";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //This is a loop for all the items that are selected in wich this data is parsed to movie objects. These objects are put in the list created above/
            while (rs.next()) {
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"), rs.getString("Status"), rs.getInt("Duration"),
                        rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
//
                movies.add(movie);
            }
            for (Movie currentMovie : movies) {
                currentMovie.setTranslations(getTranslationsForMovie(currentMovie.getId()));
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //This returns the list with movies
        return movies;
    }

    @Override
    public ArrayList<Movie> getTop10Movies() {
        openConnection();
        //Creates an arraylist and movie object. In this arraylist all the movies with the same title will be stored.
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = null;
        try{
            //This string contains the query where multiple movies can come out
            String SQL = "SELECT TOP 10 * FROM Movie ORDER BY Popularity DESC";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //This is a loop for all the items that are selected in wich this data is parsed to movie objects. These objects are put in the list created above/
            while (rs.next()) {
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"), rs.getString("Status"), rs.getInt("Duration"),
                        rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
//
                movies.add(movie);
            }
            for (Movie currentMovie : movies) {
                currentMovie.setTranslations(getTranslationsForMovie(currentMovie.getId()));
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //This returns the list with movies
        return movies;
    }
    //SELECT TOP 1 * FROM Movie
    //ORDER BY NEWID()

    @Override
    public Movie getRandomMovie() {
        openConnection();
        //Creates a movie object without any data in it
        Movie movie = null;
        try{
            //This string contains the select query for a movie
            String SQL = "SELECT TOP 1 * FROM Movie ORDER BY NEWID()";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //The selected movie will be filled in with data
            rs.next();
            movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"),rs.getString("ReleaseDate"), rs.getBoolean("Adult"), rs.getString("Status"), rs.getInt("Duration"), rs.getInt("Popularity"), rs.getString("URL"), rs.getString("Banner"));
            movie.setTranslations(getTranslationsForMovie(rs.getInt("Id")));
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //Returns the movie object created before
        return movie;
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
}
