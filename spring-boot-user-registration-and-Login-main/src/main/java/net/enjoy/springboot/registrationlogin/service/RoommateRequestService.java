package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.entity.RoommateRequest;
import net.enjoy.springboot.registrationlogin.repository.RoommateRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoommateRequestService {
    @Autowired
    private RoommateRequestRepository roommateRequestRepository;

    public void bulkUpdateImages() {
        for (long i = 1; i <= 100; i++) { // Assuming IDs start from 1
            String imageName = i + ".jpg"; // Store only the image name (e.g., "5.jpg")

            Optional<RoommateRequest> roommate = roommateRequestRepository.findById(i);
            if (roommate.isPresent()) {
                RoommateRequest r = roommate.get();
                r.setPhotoRoommate(imageName); // Save only "5.jpg" without any path
                roommateRequestRepository.save(r);
            }
        }
    }

}


