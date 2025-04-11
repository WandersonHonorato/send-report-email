package com.novaterra.SendReportEmail.service;

import com.novaterra.SendReportEmail.model.Customer;
import com.novaterra.SendReportEmail.repository.CustomerRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledShipmentService {

    private final CustomerRepository clienteRepository;

    private final EmailService emailService;

    public void sendToAll() throws MessagingException, FileNotFoundException {
        List<Customer> customers = clienteRepository.findAll();
        for (Customer customer: customers) {
            try {
                emailService.SendMedicationByEmail(customer);
            } catch (Exception e) {

                System.err.println("Error sending to " + customer.getEmail() + ": " + e.getMessage());
            }
        }
    }

}
