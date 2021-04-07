package nl.avans.moviemenace.dataLayer.Rooms.RoomDAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import nl.avans.moviemenace.dataLayer.Rooms.Entities.MovieEntity;

@Dao
public interface RoomMovieDAO{

    @Query("SELECT * FROM MovieEntity WHERE movieID = :movieID")
    MovieEntity getMovieByID(int movieID);

    @Query("SELECT * FROM MovieEntity ORDER BY popularity DESC")
    List<MovieEntity> getAllMovies();

    @Query("SELECT * FROM MovieEntity ORDER BY Popularity DESC LIMIT 10")
    List<MovieEntity> getTop10Movies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovies(MovieEntity[] movies);

}
