package nl.avans.moviemenace.dataLayer.API;

import androidx.annotation.NonNull;

import java.util.List;

import nl.avans.moviemenace.domain.Movie;

// Class using GSON to read JSON data. Automatically creates Movie class objects with returned results.
public class TrendingMoviesApiResponse {

    private int page;
    private List<Movie> results;

    public TrendingMoviesApiResponse(int page, List<Movie> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieApiResponse{" +
                "page=" + page +
                ", results=" + results +
                '}';
    }
}
