package nl.avans.moviemenace.dataLayer.SQL;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.CinemaDAO;
import nl.avans.moviemenace.domain.Cinema;

public class SQLCinemaDAO extends DatabaseConnection implements CinemaDAO {
    @Override
    public Cinema getCinema(String name) {
        openConnection();
        //This creates a cinema wich will contain the data later
        Cinema cinema = null;
        try{
            //This string contains the SQL query for searching the cinema
            String SQL = "SELECT * FROM Cinema WHERE Name = '" + name + "'";
            //This executes the query
            executeSQLSelectStatement(SQL);
            //The cinema object created before will be filled in with data here
            cinema = new Cinema(rs.getString("Name"), rs.getString("Place"), rs.getInt("NumberOfRooms"));
        } catch(Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //Returns the cinema object
        return cinema;
    }
}
