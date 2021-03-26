package nl.avans.moviemenace.dataLayer.API;

import androidx.annotation.NonNull;

import java.util.List;

import nl.avans.moviemenace.domain.Movie;

public class MovieApiResponse {

    private int page;
    private List<Movie> results;

    public MovieApiResponse(int page, List<Movie> results) {
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
