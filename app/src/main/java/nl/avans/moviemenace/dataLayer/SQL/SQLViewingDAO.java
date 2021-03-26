package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.domain.Viewing;

public class SQLViewingDAO implements ViewingDAO {
    @Override
    public Viewing getViewing(int viewID) {
        return null;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForFilm(int movieID) {
        return null;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForRoom(int roomNumber) {
        return null;
    }

    @Override
    public ArrayList<Viewing> getViewingsForDate(LocalDate date) {
        return null;
    }
}
