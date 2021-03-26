package nl.avans.moviemenace.dataLayer;

import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.dataLayer.IDAO.CinemaDAO;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.dataLayer.IDAO.RoomDAO;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;

public interface DAOFactory {
    AccountDAO createAccountDAO();
    CinemaDAO createCinemaDAO();
    MovieDAO createMovieDAO();
    MovieListDAO createMovieListDAO();
    RoomDAO createRoomDAO();
    TicketDAO createTicketDAO();
    ViewingDAO createViewingDAO();
}
