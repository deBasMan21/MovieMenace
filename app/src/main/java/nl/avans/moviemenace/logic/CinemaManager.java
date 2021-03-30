package nl.avans.moviemenace.logic;

import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.CinemaDAO;
import nl.avans.moviemenace.domain.Cinema;

public class CinemaManager {

    private CinemaDAO cinemaDAO;

    public CinemaManager(DAOFactory daoFactory) {
        this.cinemaDAO = daoFactory.createCinemaDAO();
    }

    public Cinema getCinema(String name) {
        return cinemaDAO.getCinema(name);
    }
}
