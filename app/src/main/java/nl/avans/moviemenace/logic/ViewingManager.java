package nl.avans.moviemenace.logic;

import java.time.LocalTime;

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


}
