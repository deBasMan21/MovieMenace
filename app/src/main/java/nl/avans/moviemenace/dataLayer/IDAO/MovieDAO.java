package nl.avans.moviemenace.dataLayer.IDAO;

import nl.avans.moviemenace.domain.Movie;

public interface MovieDAO {
    Movie getMovie(int id);
    Movie getMovieByTitle(String title);
}
