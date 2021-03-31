package nl.avans.moviemenace.logic;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.RoomDAO;
import nl.avans.moviemenace.domain.Room;

public class RoomManager {

    private RoomDAO roomDAO;

    public RoomManager(DAOFactory daoFactory) {
        this.roomDAO = daoFactory.createRoomDAO();
    }

    public Room getRoom(int roomNumber) {
        return roomDAO.getRoom(roomNumber);
    }
}
