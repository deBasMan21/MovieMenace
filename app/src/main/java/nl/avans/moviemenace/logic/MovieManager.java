package nl.avans.moviemenace.logic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import nl.avans.moviemenace.dataLayer.API.MovieAPI;
import nl.avans.moviemenace.dataLayer.API.MovieApiResponse;
import nl.avans.moviemenace.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MovieManager implements Callback<MovieApiResponse> {

    private final String TAG = this.getClass().getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w500/";

    private MovieControllerListener listener;

    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieManager(MovieControllerListener listener) {
        this.listener = listener;

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public void loadTrendingMoviesPerWeek() {
        Call<MovieApiResponse> call = movieAPI.loadTrendingMoviesByWeek();
        call.enqueue(this);
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        Log.d(TAG, "onResponse() status code: " + response.code());

        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            List<Movie> movies = response.body().getResults();
            listener.onMoviesAvailable(movies);
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e(TAG, "onFailure" + t.getMessage());
    }

    public interface  MovieControllerListener {
        void onMoviesAvailable(List<Movie> movies);
    }

}
