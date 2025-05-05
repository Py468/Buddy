package net.enjoy.springboot.registrationlogin.controller;

import jakarta.persistence.Column;
import net.enjoy.springboot.registrationlogin.entity.Room;
import net.enjoy.springboot.registrationlogin.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms/add")
    public String showaddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "addRoom";
    }

    @PostMapping("/rooms/add")
    public String saveRoom(@ModelAttribute("room") Room room, @RequestParam("imageFile")MultipartFile imageFile)throws IOException, IOException {
        if (!imageFile.isEmpty()) {
            room.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes())); // Convert and set image as Base64
        }
        roomService.saveRoom(room,imageFile);
        return "redirect:/rooms";

    }

    @GetMapping("/rooms")
    public String roomList(Model model) {
        List<Room> room = roomService.getAllRoom();
        model.addAttribute("rooms", room);

        return "RoomList";

    }

    @GetMapping(value = "/{roomId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long roomId) {
        Optional<Room> roomOptional = roomService.getRoom(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            byte[] imageBytes = java.util.Base64.getDecoder().decode(room.getImage());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
        }
    }

    //********** Edit Room **************/
    @GetMapping("/rooms/{id}/edit")
    public String showEditRoomForm(@PathVariable Long id, Model model) {
        Optional<Room> roomOptional = roomService.getRoom(id);
        if (roomOptional.isPresent()) {
            model.addAttribute("room", roomOptional.get());
            return "editroom";  // Ensure this matches your Thymeleaf template name
        } else {
            model.addAttribute("errorMessage", "Room with ID " + id + " not found.");
            return "error";  // Ensure you have an error.html template
        }
    }


    // Save updated room
    @PostMapping("/rooms/{id}/edit")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room, @RequestParam("imageFile") MultipartFile file) throws IOException {
        room.setId(id);  // Set the ID to ensure it's updated properly

        // If a new image is uploaded, convert and set it
        if (!file.isEmpty()) {
            room.setImage(Base64.getEncoder().encodeToString(file.getBytes())); // Convert to Base64
        }

        roomService.saveRoom(room, file);  // Save the room (along with the updated image, if any)
        return "redirect:/rooms";  // Redirect to room list after saving
    }
    //Delete Room
    @GetMapping("rooms/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);  // Call the service method to delete the room by id
        return "redirect:/rooms";    // Redirect to the room list after deletion
    }

    @GetMapping("/rooms/{id}")
    public String viewRoomPost(@PathVariable Long id, Model model) {
        // Retrieve the room by ID
        Room room = roomService.getRoom(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + id));

        // Add the room to the model
        model.addAttribute("room", room);

        // Generate Base64 image URL for Thymeleaf
        if (room.getImage() != null) {
            String imageBase64 = "data:image/jpeg;base64," + room.getImage();
            model.addAttribute("imageBase64", imageBase64); // Add the Base64 URL to the model
        }

        return "viewroom"; // Refer to the Thymeleaf template for viewing room details
    }


    //******* Filter Rooms on the basis of City and Price range
    @GetMapping("/rooms/filter")
    public String filterRooms(@RequestParam(required = false)String city,
                              @RequestParam(required = false)Double minPrice,
                              @RequestParam(required = false)Double maxPrice,Model model){
        List<Room> filter=roomService.filterRoom(city,minPrice,maxPrice);
        model.addAttribute("rooms",filter);
        //pass filter criteria back to the template
        model.addAttribute("selectedCity",city);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("maxPrice",maxPrice);
        return "authuser/roomslist";
    }
    @GetMapping("/rooms/details/{id}")
    public String showDetails(@PathVariable Long id,Model model){
        Room room=roomService.getRoom(id). orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + id));
        if(room.getImage()!=null){
            String imageBase64 = "data:image/jpeg;base64," + room.getImage();
            model.addAttribute("imageBase64", imageBase64);
        }
        model.addAttribute("room",room);
        return "RoomDetails";
    }
    @GetMapping("/rooms/filter/title")
    public String findCategory(@RequestParam("category")String category,Model model){
        List<Room> rooms;
        if("flat".equalsIgnoreCase(category)){
            rooms=roomService.findFlats();
        }
        else if("hostel".equalsIgnoreCase(category)){
            rooms=roomService.findHostel();
        }
        else if("pg".equalsIgnoreCase(category)){
            rooms=roomService.findPG();
        }
        else{
            rooms=roomService.findFlats();
        }
        model.addAttribute("rooms",rooms);
        return "authuser/roomslist :: room-container";
    }



}