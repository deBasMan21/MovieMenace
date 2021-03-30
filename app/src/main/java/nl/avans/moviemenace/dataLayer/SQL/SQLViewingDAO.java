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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.openConnection();
        System.out.println("The database connection is: " + databaseConnection.openConnection());
        //creates a viewing to contain the data
        Viewing viewing = null;
        try{
            //this string contains the sql query to select a viewing
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE ViewID = " + viewID;
            //this executes the query
            executeSQLSelectStatement(SQL);
            //creates localdatetime object to use in the viewing object
            LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
            //creates room object to use in the viewing object
            Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
            //adds the data to the viewing
            viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the viewing
        return viewing;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForFilm(int movieID) {
        //creates an arraylist of viewings to contain all upcoming viewings for a specific film
        ArrayList<Viewing> viewingsForFilm = new ArrayList<>();
        try{
            //this string contains the sql query to select all upcoming viewings for a film
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() >= V.Date AND V.MovieID = " + movieID;
            //this executes the query
            executeSQLSelectStatement(SQL);
            //creates a loop
            while(rs.next()){
                //creates localdattime object to use for the viewing
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                //creates room object to use for the viewing
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                //creates the viewing object will all data in it
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);
                //checks if the viewing is later today or not
                if(viewing.getDate().isAfter(LocalDateTime.now())){
                    //adds the viewing to the list if its later today
                    viewingsForFilm.add(viewing);
                }
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list of viewings
        return viewingsForFilm;
    }

    @Override
    public ArrayList<Viewing> getUpcomingViewingsForRoom(int roomNumber) {
        //creates an arraylist to contain all viewings that are upcoming in a room
        ArrayList<Viewing> viewingsForRoom = new ArrayList<>();
        try{
            //this string contains the query to select all upcoming viewings in a room
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() >= V.Date AND V.RoomNumber = " + roomNumber;
            //this executes the query
            executeSQLSelectStatement(SQL);
            //creates a loop
            while(rs.next()){
                //creates localdatetime object to use in viewing
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                //creates room object to use in a viewing
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                //creates viewing with all data in it
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);
                //checks if the viewing is in the future
                if(viewing.getDate().isAfter(LocalDateTime.now())){
                    //adds the viewing when it is upcoming
                    viewingsForRoom.add(viewing);
                }
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list of viewings for a room
        return viewingsForRoom;
    }

    @Override
    public ArrayList<Viewing> getViewingsForToday() {
        //creates an arraylist to contain all viewings for a day
        ArrayList<Viewing> viewingsForDate = new ArrayList<>();
        try{
            //this string contains the sql query to select all viewings for today
            String SQL = "SELECT * FROM Viewing AS V INNER JOIN Room AS R ON R.RoomNumber = V.RoomNumber WHERE GETDATE() = V.Date";
            //this executes the query
            executeSQLSelectStatement(SQL);
            //creates a loop
            while(rs.next()){
                //cretaes localdatetime object to use in viewing
                LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")), LocalTime.parse(rs.getString("Time")));
                //creates room object to use in viewing
                Room room = new Room(rs.getInt("RoomNumber"), rs.getInt("NumberOfSeats"), rs.getBoolean("[3D]"), rs.getInt("NumberOfRows"));
                //creates viewing with all the data
                Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble("Price"), rs.getBoolean("[3D]"), room);
                //adds the viewing to the list
                viewingsForDate.add(viewing);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list with viewings
        return viewingsForDate;
    }
}
