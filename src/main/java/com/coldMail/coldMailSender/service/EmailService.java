package com.coldMail.coldMailSender.service;

import com.coldMail.coldMailSender.config.MailConfig;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;

public class EmailService {

    public void sendEmail(String recipient,
                          String subject,
                          String htmlBody) throws Exception {

        Session session = MailConfig.getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MailConfig.getUsername()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
        message.setSubject(subject);

        // HTML Part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html; charset=utf-8");

        // Attachment
        MimeBodyPart attachmentPart = new MimeBodyPart();

        File resumeFile = new File(
                getClass().getClassLoader()
                        .getResource("Narayana_Resume.pdf")
                        .toURI()
        );

        attachmentPart.attachFile(resumeFile);
        attachmentPart.setFileName("Narayana_Rao_Mahendrakar_Resume.pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
