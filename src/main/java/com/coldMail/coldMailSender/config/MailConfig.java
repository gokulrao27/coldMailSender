package com.coldMail.coldMailSender.config;

import jakarta.mail.*;

import jakarta.mail.PasswordAuthentication;
import java.util.Properties;

public class MailConfig {

    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";

    private static final String USERNAME = "mahendrakarnarayan7@gmail.com";
    private static final String APP_PASSWORD = "MOM@dad143";

    public static Session getSession() {

        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, APP_PASSWORD);
            }
        });
    }

    public static String getUsername() {
        return USERNAME;
    }
}
