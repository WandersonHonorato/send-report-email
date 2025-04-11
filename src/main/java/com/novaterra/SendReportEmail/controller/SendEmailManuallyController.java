package com.novaterra.SendReportEmail.controller;

import com.novaterra.SendReportEmail.service.ScheduledShipmentService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sends")
public class SendEmailManuallyController {

    private final ScheduledShipmentService scheduledService;

    @GetMapping("/medications")
    public String send() throws MessagingException, FileNotFoundException {
        scheduledService.sendToAll();
        return "Measurements Sent";
    }

}
