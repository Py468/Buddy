package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllRoom();
    Room saveRoom(Room room, MultipartFile file) throws IOException;
    Optional<Room> getRoom(Long id);
    void deleteRoom(Long id);
    List<Room>filterRoom(String city,Double minPrice,Double maxPrice);
    List<Room> findFlats();
    List<Room> findHostel();
    List<Room> findPG();
}
