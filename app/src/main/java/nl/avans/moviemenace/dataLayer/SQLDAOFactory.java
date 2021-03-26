package nl.avans.moviemenace.dataLayer;

import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.dataLayer.IDAO.CinemaDAO;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.IDAO.MovieListDAO;
import nl.avans.moviemenace.dataLayer.IDAO.RoomDAO;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLAccountDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLCinemaDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLMovieDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLMovieListDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLRoomDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLTicketDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLViewingDAO;

public class SQLDAOFactory implements DAOFactory {
    @Override
    public AccountDAO createAccountDAO() {
        return new SQLAccountDAO();
    }

    @Override
    public CinemaDAO createCinemaDAO() {
        return new SQLCinemaDAO();
    }

    @Override
    public MovieDAO createMovieDAO() {
        return new SQLMovieDAO();
    }

    @Override
    public MovieListDAO createMovieListDAO() {
        return new SQLMovieListDAO();
    }

    @Override
    public RoomDAO createRoomDAO() {
        return new SQLRoomDAO();
    }

    @Override
    public TicketDAO createTicketDAO() {
        return new SQLTicketDAO();
    }

    @Override
    public ViewingDAO createViewingDAO() {
        return new SQLViewingDAO();
    }
}
