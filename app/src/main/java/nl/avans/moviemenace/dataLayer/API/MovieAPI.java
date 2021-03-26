package nl.avans.moviemenace.dataLayer.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {

    String API_KEY = "e26a8690ca9f73d9faf6d79bbbc0f4f1";

    @GET("trending/movie/week?api_key=" + API_KEY)
    Call<MovieApiResponse> loadTrendingMoviesByWeek();
}
