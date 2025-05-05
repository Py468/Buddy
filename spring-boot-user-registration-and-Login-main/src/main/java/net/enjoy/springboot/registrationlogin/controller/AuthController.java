package net.enjoy.springboot.registrationlogin.controller;

import jakarta.validation.Valid;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.service.SmsService;
import net.enjoy.springboot.registrationlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;
    @Autowired
    private SmsService smsService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "signup";
    }


    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        boolean isOtpValid = smsService.validateOtp(userDto.getEmail(), userDto.getOtp());
        if (!isOtpValid) {
            result.rejectValue("otp", null, "Invalid or expired OTP");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return  "signup";
        }


        smsService.clearOtp(userDto.getEmail());
        userService.saveUser(userDto);
        return "redirect:/signup?success";
    }


    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/home")
    public String homePage() {
        return "authuser/authhome";
    }

    @PostMapping("/send-otp-email")
    @ResponseBody
    public String sendOtp(@RequestParam String email) {
       smsService.sendOtp(email);
       return "OTP send to your email";
    }
}