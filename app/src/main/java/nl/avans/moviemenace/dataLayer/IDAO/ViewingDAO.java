package nl.avans.moviemenace.dataLayer.IDAO;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.domain.Viewing;

public interface ViewingDAO {
    Viewing getViewing(int viewID);
    ArrayList<Viewing> getUpcomingViewingsForFilm(int movieID);
    ArrayList<Viewing> getUpcomingViewingsForRoom(int roomNumber);
    ArrayList<Viewing> getViewingsForToday();
}
