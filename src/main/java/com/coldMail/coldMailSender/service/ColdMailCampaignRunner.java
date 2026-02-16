package com.coldMail.coldMailSender.service;

import com.coldMail.coldMailSender.config.ColdMailProperties;
import com.coldMail.coldMailSender.model.EmailTemplate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

@Component
public class ColdMailCampaignRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColdMailCampaignRunner.class);

    private final TemplateService templateService;
    private final EmailService emailService;
    private final ColdMailProperties properties;
    private final MailProperties mailProperties;

    public ColdMailCampaignRunner(TemplateService templateService,
                                  EmailService emailService,
                                  ColdMailProperties properties,
                                  MailProperties mailProperties) {
        this.templateService = templateService;
        this.emailService = emailService;
        this.properties = properties;
        this.mailProperties = mailProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!properties.isEnabled()) {
            LOGGER.info("Cold mail campaign disabled by configuration.");
            return;
        }

        if (isBlank(mailProperties.getUsername()) || isBlank(mailProperties.getPassword())) {
            LOGGER.warn("Missing mail credentials. Set MAIL_USERNAME and MAIL_PASSWORD before running campaign.");
            return;
        }

        List<String> recipients = properties.getRecipients()
                .stream()
                .filter(recipient -> recipient != null && !recipient.isBlank())
                .toList();

        if (recipients.isEmpty()) {
            LOGGER.warn("No recipients configured. Set RECIPIENTS (comma-separated) or cold-mail.recipients. Campaign skipped.");
            return;
        }

        EmailTemplate template = templateService.loadTemplate(properties.getTemplate());

        for (String recipient : recipients) {
            String personalizedBody = templateService.personalize(template.getBody(), recipient);
            emailService.sendEmail(recipient, template.getSubject(), personalizedBody);
            LOGGER.info("Sent to: {}", recipient);

            if (properties.getDelayMillis() > 0) {
                Thread.sleep(properties.getDelayMillis());
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
