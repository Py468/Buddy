package net.enjoy.springboot.registrationlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class sendEmail {

    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // Always send email from 'aayushtyagi078@gmail.com'
            message.setFrom("roombuddyfinder@gmail.com");
            message.setTo(to);  // Target email (the recipient's email address)
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }
}
