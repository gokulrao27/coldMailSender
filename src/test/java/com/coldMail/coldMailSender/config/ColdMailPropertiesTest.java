package com.coldMail.coldMailSender.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ColdMailPropertiesTest {

    @Test
    void recipientsShouldBeStoredAsConfigured() {
        ColdMailProperties properties = new ColdMailProperties();
        properties.setRecipients(List.of("first@example.com", "second@example.com"));

        assertEquals(List.of("first@example.com", "second@example.com"), properties.getRecipients());
    }
}
