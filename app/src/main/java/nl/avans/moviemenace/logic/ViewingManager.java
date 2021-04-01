package nl.avans.moviemenace.logic;

import java.time.LocalTime;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Viewing;

public class ViewingManager {

    private ViewingDAO viewingDAO;

    public ViewingManager(DAOFactory factory) {
        viewingDAO = factory.createViewingDAO();
    }

    public LocalTime getEndTime(Viewing viewing, Movie movie) {
        int duration = movie.getDuration();
        LocalTime startTime = viewing.getDate().toLocalTime();

        return startTime.plusMinutes(duration);
    }

    public Viewing getViewing(int viewID) {
        return viewingDAO.getViewing(viewID);
    }
    public ArrayList<Viewing> getUpcomingViewingsForFilm(int movieID) {
        return viewingDAO.getUpcomingViewingsForFilm(movieID);
    }
    public ArrayList<Viewing> getUpcomingViewingsForRoom(int roomNumber) {
        return viewingDAO.getUpcomingViewingsForRoom(roomNumber);
    }
    public ArrayList<Viewing> getViewingsForToday() {
        return viewingDAO.getViewingsForToday();
    }
    public int getTakenSeats(int viewID) {
        return viewingDAO.getTakenSeats(viewID);
    }


}
