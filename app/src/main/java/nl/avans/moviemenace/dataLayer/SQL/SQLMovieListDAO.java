package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;

public class SQLMovieListDAO extends DatabaseConnection implements MovieListDAO {
    @Override
    public MovieList getMovieList(int id) {
        MovieList movieList = null;
        ArrayList<Movie> moviesInList = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM List WHERE ID = " + id;
            executeSQLSelectStatement(SQL);
            movieList = new MovieList(rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Email"));

            movieList.setMovies(getMoviesForList(id));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<MovieList> getMovieListsForAccount(String email) {
        ArrayList<MovieList> movieLists = new ArrayList<>();

        try{
            String SQL = "SELECT * FROM List WHERE Email = " + email;
            executeSQLSelectStatement(SQL);

            while(rs.next()){
                ArrayList<Movie> moviesInList = new ArrayList<>();
                MovieList movieList = new MovieList(rs.getInt("ID"), rs.getString("Name"), rs.getString("Description"), rs.getString("Email"));

                movieList.setMovies(getMoviesForList(rs.getInt("ID")));

                movieLists.add(movieList);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return movieLists;
    }

    @Override
    public ArrayList<Movie> getMoviesForList(int listID){
        ArrayList<Movie> moviesInList = new ArrayList<>();
        try{
            String SQLMovies = "SELECT * FROM Listcontent AS L INNER JOIN MOVIE AS M ON L.MovieID = M.Id WHERE ListID = " + listID;
            executeSQLSelectStatement(SQLMovies);
            while(rs.next()){
                Movie movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"), LocalDate.parse(rs.getString("ReleaseDate")), rs.getBoolean("Adult"));
                moviesInList.add(movie);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return moviesInList;
    }
}
