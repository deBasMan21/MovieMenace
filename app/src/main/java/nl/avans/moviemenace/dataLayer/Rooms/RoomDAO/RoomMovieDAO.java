package nl.avans.moviemenace.dataLayer.Rooms.RoomDAO;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.Rooms.Entities.Movie;

@Dao
public interface RoomMovieDAO{

    @Query("SELECT * FROM Movie WHERE movieID = :movieID")
    nl.avans.moviemenace.dataLayer.Rooms.Entities.Movie getMovieByID(int movieID);

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    ArrayList<Movie> getAllMovies();
}
