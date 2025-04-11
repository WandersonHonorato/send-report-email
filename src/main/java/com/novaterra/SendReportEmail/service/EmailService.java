package com.novaterra.SendReportEmail.service;

import com.novaterra.SendReportEmail.model.Customer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Getter
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String medicoesDir;

    public EmailService(JavaMailSender mailSender,
                        @Value("${medicoes.dir}") String medicoesDir) {
        this.mailSender = mailSender;
        this.medicoesDir = medicoesDir;

    }

    public void SendMedicationByEmail(Customer customer) throws MessagingException, FileNotFoundException {
        String pathPdf = medicoesDir + "/" + customer.getFantasyName() + ".pdf";
        File filePdf = new File(pathPdf);

        log.info("Enviando e-mail para: {}", customer.getEmail());
        log.info("PDF usado: {}", pathPdf);

        if (!filePdf.exists()) {
            throw new FileNotFoundException("PDF file not found for client: " + customer.getFantasyName());
        }

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(customer.getEmail());
        helper.setSubject("MEDIÇÃO | NOVATERRA AMBIENTAL | MARÇO 2025");
        helper.setText("Cliente: " + customer.getName() + ",\n\nSegue em anexo medição referente aos serviços realizados em Março/2025");
        helper.setFrom("faturamento@novaterraambiental.srv.br");

        FileSystemResource file = new FileSystemResource(filePdf);
        helper.addAttachment(filePdf.getName(), file);

        mailSender.send(msg);
    }
}
