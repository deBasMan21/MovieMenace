package nl.avans.moviemenace.logic;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.dataLayer.API.MovieAPI;
import nl.avans.moviemenace.dataLayer.API.TrendingMoviesApiResponse;
import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.Rooms.Entities.MovieEntity;
import nl.avans.moviemenace.dataLayer.Rooms.MovieDB;
import nl.avans.moviemenace.dataLayer.Rooms.RoomDAO.RoomMovieDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.ui.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Class for interacting between UI and API/Datalayer.
public class MovieManager {

    private MovieDAO movieDAO;

    public MovieManager(DAOFactory factory){
        this.movieDAO = factory.createMovieDAO();
    }

    public ArrayList<Movie> getAllMovies(){
        return movieDAO.getAllMovies();
    }





}
