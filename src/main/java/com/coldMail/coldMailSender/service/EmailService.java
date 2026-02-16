package com.coldMail.coldMailSender.service;

import com.coldMail.coldMailSender.config.ColdMailProperties;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ColdMailProperties properties;

    public EmailService(JavaMailSender mailSender, ColdMailProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
    }

    public void sendEmail(String recipient,
                          String subject,
                          String htmlBody) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        ClassPathResource attachment = new ClassPathResource(properties.getAttachmentPath());
        helper.addAttachment(attachment.getFilename(), attachment);

        mailSender.send(message);
    }
}
