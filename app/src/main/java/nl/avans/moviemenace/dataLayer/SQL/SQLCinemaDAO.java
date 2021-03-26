package nl.avans.moviemenace.dataLayer.SQL;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.CinemaDAO;
import nl.avans.moviemenace.domain.Cinema;

public class SQLCinemaDAO extends DatabaseConnection implements CinemaDAO {
    @Override
    public Cinema getCinema(String name) {
        Cinema cinema = null;
        try{
            String SQL = "SELECT * FROM Cinema WHERE Name = " + name;
            executeSQLSelectStatement(SQL);

            cinema = new Cinema(rs.getString("Name"), rs.getString("Place"), rs.getInt("NumberOfRooms"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return cinema;
    }
}
