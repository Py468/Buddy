package net.enjoy.springboot.registrationlogin.controller;

import net.enjoy.springboot.registrationlogin.entity.RoommateRequest;
import net.enjoy.springboot.registrationlogin.repository.RoommateRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminRoommateController {
    @Autowired
    private RoommateRequestRepository repository;
    @GetMapping("/admin/roommates")
    public String listRequests(Model model){
        List<RoommateRequest> pendingRequests=repository.findByApprovedFalse();
        model.addAttribute("requests",pendingRequests);
        return "/roommate/listRequests";
    }
    @PostMapping("/admin/approved/{id}")
    public String approvedRequest(@PathVariable Long id) {
        Optional<RoommateRequest> request = repository.findById(id);
        if (request.isPresent()) {
            RoommateRequest roommateRequest = request.get();
            roommateRequest.setApproved(true);
            repository.save(roommateRequest);

        }
        return "redirect:/admin/roommates";
    }
    @GetMapping("/admin/dashboard")
    public String getDashboard(){
        return "/dashboard";
    }

//    @GetMapping("/signup")
//    public String signUp(){
//        return "signup";
//    }
}
