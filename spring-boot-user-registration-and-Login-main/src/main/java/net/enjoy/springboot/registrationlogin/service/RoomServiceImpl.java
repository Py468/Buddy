package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.entity.Room;
import net.enjoy.springboot.registrationlogin.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service

public class RoomServiceImpl implements RoomService{
    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room saveRoom(Room room, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            room.setImage(Base64.getEncoder().encodeToString(file.getBytes())); // Base64 encode the image
        }
        return roomRepository.save(room);

    }

    @Override
    public Optional<Room> getRoom(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> filterRoom(String city, Double minPrice, Double maxPrice) {
        return roomRepository.findByCityAndPriceRange(city,minPrice,maxPrice);
    }

    @Override
    public List<Room> findFlats() {
        return roomRepository.findFlats();
    }

    @Override
    public List<Room> findHostel() {
        return roomRepository.findHostels();
    }

    @Override
    public List<Room> findPG() {
        return roomRepository.findPGs();
    }
}
