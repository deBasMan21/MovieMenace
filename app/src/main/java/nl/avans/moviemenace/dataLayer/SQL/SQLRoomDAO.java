package nl.avans.moviemenace.dataLayer.SQL;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.RoomDAO;
import nl.avans.moviemenace.domain.Cinema;
import nl.avans.moviemenace.domain.Room;

public class SQLRoomDAO extends DatabaseConnection implements RoomDAO {
    @Override
    public Room getRoom(int roomNumber) {
        openConnection();
        //creates a room
        Room room = null;
        try{
            //this string contains the query for selecting the room
            String SQL = "SELECT * FROM Room AS R INNER JOIN Cinema AS C ON C.Name = R.CinemaName WHERE RoomNumber = " + roomNumber;
            //this executes the query
            executeSQLSelectStatement(SQL);
            //this fills in the data for the room
            room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
            Cinema cinema = new Cinema(rs.getString("Name"), rs.getString("Place"), rs.getInt("NumberOfRooms"));
            room.setCinema(cinema);
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        //this returns the room created above
        return room;
    }
}
