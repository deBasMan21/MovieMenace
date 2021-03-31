package nl.avans.moviemenace.dataLayer.Rooms.RoomDAO;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.domain.Movie;

@Dao
public interface RoomMovieDAO{

    @Query("SELECT * FROM Movie WHERE movieID = (:id)")
    Movie getMovieByID(int id);

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    ArrayList<Movie> getAllMovies();
}
