package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.domain.Movie;

public class SQLMovieDAO extends DatabaseConnection implements MovieDAO {
    @Override
    public Movie getMovie(int id) {
        Movie movie = null;
        try{
            String SQL = "SELECT * FROM Movie WHERE Id = " + id;
            executeSQLSelectStatement(SQL);

            movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"),rs.getString("ReleaseDate"), rs.getBoolean("Adult"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public ArrayList<Movie> getMovieByTitle(String title) {
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = null;
        try{
            String SQL = "SELECT * FROM Movie WHERE Title = " + title;
            executeSQLSelectStatement(SQL);

            while (rs.next()) {
                movie = new Movie(rs.getInt("Id"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("ReleaseDate"),
                        rs.getBoolean("Adult"));
                movies.add(movie);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return movies;
    }
}
