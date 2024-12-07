package com.klef.jfsd.sdpproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdpproject.models.EmailRequest;
import com.klef.jfsd.sdpproject.service.FacultyService;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private FacultyService facultyService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) throws Exception {
        String name = emailRequest.getName();
        String toEmail = emailRequest.getEmail();
        String subject = emailRequest.getSubject();
        String message = emailRequest.getMessage();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        int otp = (int) (Math.random() * 99999); // random number generation

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom("2200090011csit@gmail.com");

        String htmlContent =
                "<h3>Contact Form Details</h3>" +
                        "<p><strong>Name:</strong> " + name + "</p>" +
                        "<p><strong>Email:</strong> " + toEmail + "</p>" +
                        "<p><strong>Subject:</strong> " + subject + "</p>" +
                        "<p><strong>Message:</strong> " + message + "</p>"+
                        "<p><strong>OTP:</strong> " + otp + "</p>";

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
        facultyService.storeOtp(toEmail, otp);
        return ResponseEntity.ok("Email sent successfully otp:"+otp);
    }
    
}
