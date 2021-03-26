package nl.avans.moviemenace.dataLayer.IDAO;

import nl.avans.moviemenace.domain.Cinema;

public interface CinemaDAO {
    Cinema getCinema(String name);
}
