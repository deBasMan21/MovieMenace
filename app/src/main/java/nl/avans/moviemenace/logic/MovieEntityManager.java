package nl.avans.moviemenace.logic;

import android.app.Application;

import java.util.List;

import nl.avans.moviemenace.dataLayer.rooms.Entities.MovieEntity;
import nl.avans.moviemenace.dataLayer.rooms.MovieDB;
import nl.avans.moviemenace.dataLayer.rooms.RoomDAO.RoomMovieDAO;
import nl.avans.moviemenace.domain.Movie;

public class MovieEntityManager {
    private MovieDB movieDB;
    private RoomMovieDAO movieDAO;

    public MovieEntityManager(Application application){
        movieDB = MovieDB.getDatabase(application);
         movieDAO = movieDB.getMovieDAO();
    }

    public void insertAllMovies(List<Movie> movies){
        movieDB.databaseWriteExecuter.execute(new Runnable() {
            @Override
            public void run() {
                movieDAO.insertMovies(convertMoviesToEntity(movies));
            }
        });
    }

    public MovieEntity[] convertMoviesToEntity(List<Movie> movies){
        MovieEntity[] moviesParsed = new MovieEntity[movies.size()];
        for(int i = 0; i < movies.size(); i++){
            Movie movie = movies.get(i);
            moviesParsed[i] = new MovieEntity(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getRelease_date().toString(), movie.isAdult(), movie.getStatus(), movie.getDuration(), movie.getPopularity());
        }
        return moviesParsed;
    }
}
