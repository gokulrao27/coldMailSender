package com.coldMail.coldMailSender.service;

import com.coldMail.coldMailSender.model.EmailTemplate;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class TemplateService {

    public EmailTemplate loadTemplate(String fileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(fileName);

        try (InputStream inputStream = resource.getInputStream()) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(inputStream);

            String subject = doc.getElementsByTagName("subject")
                    .item(0)
                    .getTextContent()
                    .trim();

            String body = doc.getElementsByTagName("body")
                    .item(0)
                    .getTextContent()
                    .trim();

            return new EmailTemplate(subject, body);
        }
    }

    public String personalize(String body, String email) {
        String name = email.split("@")[0];
        return body.replace("${name}", name);
    }
}
