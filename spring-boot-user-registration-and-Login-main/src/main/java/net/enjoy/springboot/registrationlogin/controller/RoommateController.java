package net.enjoy.springboot.registrationlogin.controller;

import com.cloudinary.Url;
import com.fasterxml.jackson.databind.ser.Serializers;
import net.enjoy.springboot.registrationlogin.entity.*;
import net.enjoy.springboot.registrationlogin.repository.RoommatePreferenceRepository;
import net.enjoy.springboot.registrationlogin.repository.RoommateRequestRepository;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;
import net.enjoy.springboot.registrationlogin.service.RoommateRequestService;
import net.enjoy.springboot.registrationlogin.service.sendEmail;
import org.hibernate.internal.util.MutableLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller

public class RoommateController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  UserRepository user;
    @Autowired
    private RoommateRequestRepository repository;
    @Autowired
    private RoommatePreferenceRepository preferenceRepository;
    @Autowired
    RoommateRequestService service;
    @Autowired
    private sendEmail emailService;



    @GetMapping("/roommate/register/buddy")
    public String showRegistrationdPageBuddy(Model model) {
        model.addAttribute("request", new RoommateRequest());
        return "roommate/roommateform";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("photoRoommate");
    }


    @PostMapping("/roommate/register/buddy")
    public String processRegistrationPageBuddy(
            @ModelAttribute RoommateRequest request,
            @RequestParam("photoFile") MultipartFile photoRoommateFile,
            Model model) throws IOException {

        if (!photoRoommateFile.isEmpty()) {
            // Use a safe folder outside /resources
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File dir = new File(uploadDir);
            System.out.println("Upload directory: " + uploadDir);

            if (!dir.exists()) dir.mkdirs();

            String originalFilename = photoRoommateFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = System.currentTimeMillis() + fileExtension;

            File dest = new File(dir, filename);
            photoRoommateFile.transferTo(dest);

            request.setPhotoRoommate(filename); // Save filename to DB
        }

        repository.save(request);
        model.addAttribute("request", request);
        model.addAttribute("successMessage", "Request sent successfully");
        return "roommate/roommateform";
    }
    @GetMapping("/images/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path imagePath = Paths.get(System.getProperty("user.dir") + "/uploads/" + filename);
        Resource resource = new UrlResource(imagePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) //
                .body(resource);
    }



    @GetMapping("/roommate/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<RoommateRequest> requestOptional = repository.findById(id);
        if (requestOptional.isPresent()) {
            model.addAttribute("request", requestOptional.get());
            return "/roommate/editroommate"; // Separate edit form
        } else {
            model.addAttribute("errorMessage", "Roommate request not found!");
            return "/roommate/roommateform";
        }
    }


    @GetMapping("/roommate/list")
    public String listRoommateRequests(Model model) {
        List<RoommateRequest> requests = repository.findAll();
        model.addAttribute("requests", requests);
        return "/roommate/roommatetable"; // This is the Thymeleaf template
    }




    @GetMapping("/roommate/approvedlist")
    public String showListApprovedBuddy(Model model) {
        List<RoommateRequest> approvedRequests = repository.findByApprovedTrue();
        model.addAttribute("roommates", approvedRequests);
        return "/roommate/roommatelist";
    }

    @GetMapping("/roommate/{id}/details")
    public String viewDetails(@PathVariable Long id, Model model) {
        // Retrieve the roommate using the repository
        RoommateRequest roommate = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Roommate not found for ID: " + id));


        model.addAttribute("roommate", roommate);


        return "roommate/viewDetails";
    }


//    @GetMapping("/roommate/photo/{filename}")
//    public ResponseEntity<Resource> getRoommatePhoto(@PathVariable String filename) {
//        try {
//            Path imagePath = Paths.get("src/main/resources/static/css/images").resolve(filename).normalize();
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                // Auto-detect content type (JPG, PNG, etc.)
//                String contentType = Files.probeContentType(imagePath);
//                return ResponseEntity.ok()
//                        .contentType(MediaType.parseMediaType(contentType != null ? contentType : "image/jpg"))
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }




    @GetMapping("/roommate/photo/{filename}")
    public ResponseEntity<Resource> getRoommatePhoto(@PathVariable String filename) {
        try {

            Path imagePath = Paths.get("src/main/resources/static/css/images").resolve(filename).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Adjust for PNG if needed
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

@GetMapping("/roommate/form")
    public String showForm(Model model,Principal principal){

        String email=principal.getName();
        RoommatePreference existUser=preferenceRepository.findByEmail(email);
    if (existUser != null) {
        model.addAttribute("roommatePreferences", existUser); // Pre-fill if exists
    } else {
        RoommatePreference newPref = new RoommatePreference();
        newPref.setEmail(email); // set email from logged-in user
        model.addAttribute("roommatePreferences", newPref);
    }

    return "roommate/reccommendationform";
}
    @PostMapping("/roommate/savePreference")
    public String savePreference(@ModelAttribute RoommatePreference preference, Principal principal) {
        String email = principal.getName();
        RoommatePreference existing = preferenceRepository.findByEmail(email);

        if (existing != null) {
            // Update existing
            preference.setId(existing.getId());
        }

        preference.setEmail(email); // Always set correct user email
        preferenceRepository.save(preference);
        return "redirect:/roommate/reccommend"; // Go to recommendation directly
    }
@GetMapping("/roommate/reccommend")
    public String getReccommendation(Model model,Principal principal){
    String email = principal.getName();

    // 2. Fetch saved preference
    RoommatePreference preference = preferenceRepository.findByEmail(email);

    if (preference == null) {
        // 3. Redirect if no preference
        return "redirect:/roommate/form";
    }
        String apiUrl="http://127.0.0.1:5000/recommend";
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<RoommatePreference>request=new HttpEntity<>(preference,headers);
    ResponseEntity<ApiWrapper> response=restTemplate.postForEntity(apiUrl,request,ApiWrapper.class);

    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        model.addAttribute("reccommendation", response.getBody().getMatches());
    }

    else{
        model.addAttribute("error","Unable to fetch reccommendation at these time. ");
    }
    return "roommate/reccommendation";
}


    @PostMapping("/roommate/connect")
    public String connectWithRoommate(@RequestParam("targetRoommateId") Long targetRoommateId,Principal principal) {
        String originalSender=principal.getName();
        RoommatePreference sender=preferenceRepository.findByEmail(originalSender);
        RoommateRequest receiver=repository.findById(targetRoommateId).orElse(null);
        User sendUser=user.findByEmail(originalSender);
        System.out.println("==> In /roommate/connect with ID: " + targetRoommateId);

        // Use fixed sender email
        String mediatorEmail = "roombuddyfinder@gmail.com";

        if (receiver != null && sender != null) {
            System.out.println("==> Preparing email content");

            String message = "Hi " + receiver.getName() + ",\n\n"
                    + "Someone is interested in being your roommate! Here's their info:\n\n"
                    + "Phone number:"+sendUser.getPhoneNumber()+"\n\n"
                    + "Email: " + sender.getEmail() + "\n"
                    + "Budget: " + sender.getBudgetRange() + "\n"
                    + "Sleep Schedule: " + sender.getSleepSchedule() + "\n"
                    + "Interests: " + sender.getInterests() + "\n\n"
                    + "You can reply to this email to connect with them directly!";

            emailService.sendEmail(
                    receiver.getEmail(),              // To
                    "New Roommate Interest!",         // Subject
                    message                           // Body
            );

            System.out.println("Email sent to " + receiver.getEmail());
        } else {
            System.out.println("Either sender or receiver is null.");
        }

        return "roommate/success";
    }



}
