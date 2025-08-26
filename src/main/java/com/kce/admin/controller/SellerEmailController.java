package com.kce.admin.controller;

import java.time.LocalDateTime;

import com.kce.admin.service.AdminSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.kce.admin.dto.SystemLogDTO;
import com.kce.admin.feign.SystemLogClient;
import com.kce.admin.model.EmailRequest;
import com.kce.admin.model.SellerResponse;
import com.kce.admin.repository.SellerEmailRepository;

@RestController
@RequestMapping("/api/admin")
public class SellerEmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SystemLogClient systemLogClient;

    @Autowired
    private SellerEmailRepository sellerEmailRepository; // ✅ Inject repository

    @Autowired
    private AdminSellerService adminSellerService;

    @PostMapping("/email/send")
    public String sendEmail(@RequestBody EmailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());
            mailSender.send(message);

            String status = request.getSubject().contains("Accepted") ? "Accepted" : "Rejected";

            String logType = status.equals("Accepted") ? "Accepted Request" : "Rejected Request";
            String logMessage = status.equals("Accepted")
                    ? "Request Accepted from " + request.getTo()
                    : "Request Rejected from " + request.getTo();

            SystemLogDTO log = new SystemLogDTO();
            log.setType(logType);
            log.setMessage(logMessage);
            log.setRole("Admin");
            log.setRoleId("ADM-6E85EBEF");
            systemLogClient.createLog(log);

            return "Email sent successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email.";
        }
    }

    @PostMapping("/response/save")
    public ResponseEntity<?> saveResponse(@RequestBody SellerResponse response) {
        try {
            SellerResponse savedResponse = adminSellerService.saveSellerResponse(response);
            return ResponseEntity.ok("Response saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save response: " + e.getMessage());
        }
    }

    @GetMapping("/response/check/{email}/{status}")
    public ResponseEntity<Boolean> checkResponseExists(@PathVariable String email, @PathVariable String status) {
        boolean exists = adminSellerService.findSellerResponseByEmailAndStatus(email, status).isPresent();
        return ResponseEntity.ok(exists);
    }
}