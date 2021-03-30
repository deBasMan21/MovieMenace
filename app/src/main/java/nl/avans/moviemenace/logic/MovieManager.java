package nl.avans.moviemenace.logic;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import nl.avans.moviemenace.dataLayer.API.MovieAPI;
import nl.avans.moviemenace.dataLayer.API.TrendingMoviesApiResponse;
import nl.avans.moviemenace.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Class for interacting between UI and API/Datalayer.
public class MovieManager implements Callback<TrendingMoviesApiResponse> {

    private final String TAG = this.getClass().getSimpleName();

    // URLs for connecting with the API of The Movie Database.
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w500/";

//    private MovieControllerListener listener;

    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieManager(/* MovieControllerListener listener */) {
//        this.listener = listener;

        // Creates a new GSON builder.
        gson = new GsonBuilder()
                .setLenient()
                .create();

        // Creates the correct Retrofit builder with the base url needed for connecting to the
        // API. Also gets the GSON for converting JSON results to classes.
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Retrofit uses the movieAPI interface to get the correct configuration for getting data
        // from the API.
        movieAPI = retrofit.create(MovieAPI.class);
    }

    // Method for starting the call to get data from the API.
    public void loadTrendingMoviesPerWeek() {
        Call<TrendingMoviesApiResponse> call = movieAPI.loadTrendingMoviesByWeek();
        call.enqueue(this);
    }

    // Returns a response on success. Automatically converts JSON data to defined classes with the correct variable names.
    @Override
    public void onResponse(Call<TrendingMoviesApiResponse> call,
                           Response<TrendingMoviesApiResponse> response) {
        Log.d(TAG, "onResponse() status code: " + response.code());

        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            List<Movie> movies = response.body().getResults();
            for (Movie movie : movies) {
                Log.d(TAG, movie.toString());
            }
//            listener.onMoviesAvailable(movies);
        } else {
            Log.e(TAG, "Not successful! Message: " + response.message());
        }
    }

    // Returns an error message if call failed.
    @Override
    public void onFailure(@NonNull Call<TrendingMoviesApiResponse> call, Throwable t) {
        Log.e(TAG, "onFailure" + t.getMessage());
    }

    // Interface for sending data to the correct class that implements the interface.
    public interface MovieControllerListener {
        void onMoviesAvailable(List<Movie> movies);
    }



}
