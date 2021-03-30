package nl.avans.moviemenace.dataLayer.API;

import retrofit2.Call;
import retrofit2.http.GET;

// Interface for getting data from The Movie Database.
public interface MovieAPI {

    // API key for connecting to The Movie Database for getting movies and movie data.
    String API_KEY = "e26a8690ca9f73d9faf6d79bbbc0f4f1";

    // Method using Retrofit 2 to connect to a webhost and get JSON as a response for GSON.
    @GET("trending/movie/week?api_key=" + API_KEY)
    Call<TrendingMoviesApiResponse> loadTrendingMoviesByWeek();
}
