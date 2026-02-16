package com.coldMail.coldMailSender.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ColdMailPropertiesTest {

    @Test
    void resolveRecipientsShouldMergeCsvAndListAndTrimValues() {
        ColdMailProperties properties = new ColdMailProperties();
        properties.setRecipients(List.of(" first@example.com ", "second@example.com"));
        properties.setRecipientsCsv("\"third@example.com\", second@example.com ,  ,\"fourth@example.com\"");

        List<String> resolved = properties.resolveRecipients();

        assertEquals(List.of(
                "first@example.com",
                "second@example.com",
                "third@example.com",
                "fourth@example.com"
        ), resolved);
    }
}
