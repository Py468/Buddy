package net.enjoy.springboot.registrationlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsService {
    @Autowired
    private JavaMailSender mailSender;
    private Map<String, String> otpMap = new HashMap<>();
    public void sendOtp(String toEmail) {
        String otp = String.valueOf((int) ((Math.random() * 900000) + 100000));
        otpMap.put(toEmail, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP for RoomBuddyFinder");
        message.setText("Your OTP is: " + otp);

        try {
            mailSender.send(message);
            System.out.println("OTP sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send OTP to: " + toEmail);
            e.printStackTrace();
        }
    }


    public boolean validateOtp(String email,String inputOtp){
        return inputOtp!=null && inputOtp.equals(otpMap.get(email));
    }
    public void clearOtp(String email){
        otpMap.remove(email);
    }
}
