package com.coldMail.coldMailSender.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coldMail.coldMailSender.model.EmailTemplate;
import org.junit.jupiter.api.Test;

class TemplateServiceTest {

    private final TemplateService templateService = new TemplateService();

    @Test
    void loadTemplateShouldReadSubjectAndBody() throws Exception {
        EmailTemplate template = templateService.loadTemplate("coldmail.xml");

        assertEquals("Java / Full Stack Developer | 5 YOE | Open to Full-Time / W2 / Contract", template.getSubject());
    }

    @Test
    void personalizeShouldReplaceNameToken() {
        String body = "Hello ${name}";

        String personalized = templateService.personalize(body, "john.doe@example.com");

        assertEquals("Hello john.doe", personalized);
    }
}
