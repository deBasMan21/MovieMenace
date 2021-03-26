package nl.avans.moviemenace.dataLayer.SQL;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.RoomDAO;
import nl.avans.moviemenace.domain.Room;

public class SQLRoomDAO extends DatabaseConnection implements RoomDAO {
    @Override
    public Room getRoom(int roomNumber) {
        Room room = null;
        try{
            String SQL = "SELECT * FROM Room WHERE RoomNumber = " + roomNumber;
            executeSQLSelectStatement(SQL);

            room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return room;
    }
}
