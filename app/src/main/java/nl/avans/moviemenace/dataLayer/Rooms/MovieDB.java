package nl.avans.moviemenace.dataLayer.Rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nl.avans.moviemenace.dataLayer.Rooms.Entities.MovieEntity;
import nl.avans.moviemenace.dataLayer.Rooms.RoomDAO.RoomMovieDAO;


@Database(entities = MovieEntity.class, version = 1)
public abstract class MovieDB extends RoomDatabase {

    private static volatile MovieDB INSTANCE;
    public static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(4);

    abstract public RoomMovieDAO getMovieDAO();

    public static MovieDB getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MovieDB.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDB.class, "movie_app_db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
