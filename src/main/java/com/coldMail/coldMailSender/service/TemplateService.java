package com.coldMail.coldMailSender.service;

import com.coldMail.coldMailSender.model.EmailTemplate;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.InputStream;

public class TemplateService {

    public EmailTemplate loadTemplate(String fileName) throws Exception {

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        DocumentBuilder builder = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder();

        Document doc = builder.parse(inputStream);

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

    public String personalize(String body, String email) {
        String name = email.split("@")[0];
        return body.replace("${name}", name);
    }
}
