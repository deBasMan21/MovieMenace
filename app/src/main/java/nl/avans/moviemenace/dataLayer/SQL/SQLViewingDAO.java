package nl.avans.moviemenace.dataLayer.SQL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.domain.Room;
import nl.avans.moviemenace.domain.Viewing;

public class SQLViewingDAO extends DatabaseConnection implements ViewingDAO {
    @Override
    public Viewing getViewing(int viewID) {
        Viewing viewing = null;
        try{
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE ViewID = " + viewID;
            executeSQLSelectStatement(SQL);

            LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
            Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));

            viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);
        } catch (Exception e){
            e.printStackTrace();
        }
        return viewing;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForFilm(int movieID) {
        ArrayList<Viewing> viewingsForFilm = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() >= V.Date AND V.MovieID = " + movieID;
            executeSQLSelectStatement(SQL);

            while(rs.next()){
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);

                if(viewing.getDate().isAfter(LocalDateTime.now())){
                    viewingsForFilm.add(viewing);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return viewingsForFilm;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForRoom(int roomNumber) {
        ArrayList<Viewing> viewingsForRoom = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() >= V.Date AND V.RoomNumber = " + roomNumber;
            executeSQLSelectStatement(SQL);

            while(rs.next()){
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);

                if(viewing.getDate().isAfter(LocalDateTime.now())){
                    viewingsForRoom.add(viewing);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return viewingsForRoom;
    }

    @Override
    public ArrayList<Viewing> getViewingsForToday() {
        ArrayList<Viewing> viewingsForDate = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() = V.Date";
            executeSQLSelectStatement(SQL);

            while(rs.next()){
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);

                viewingsForDate.add(viewing);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return viewingsForDate;
    }
}
