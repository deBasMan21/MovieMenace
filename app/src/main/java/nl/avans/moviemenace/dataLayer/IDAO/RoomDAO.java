package nl.avans.moviemenace.dataLayer.IDAO;

import nl.avans.moviemenace.domain.Room;

public interface RoomDAO {
    Room getRoom(int roomNumber);
}
