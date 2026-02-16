package com.coldMail.coldMailSender.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cold-mail")
public class ColdMailProperties {

    private boolean enabled = true;
    private String template = "coldmail.xml";
    private String attachmentPath = "Narayana_Resume.pdf";
    private long delayMillis = 4000;
    private String recipientsCsv = "";
    private List<String> recipients = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public long getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    public String getRecipientsCsv() {
        return recipientsCsv;
    }

    public void setRecipientsCsv(String recipientsCsv) {
        this.recipientsCsv = recipientsCsv;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public List<String> resolveRecipients() {
        List<String> mergedRecipients = new ArrayList<>();

        if (recipients != null) {
            mergedRecipients.addAll(recipients);
        }

        if (recipientsCsv != null && !recipientsCsv.isBlank()) {
            mergedRecipients.addAll(Arrays.asList(recipientsCsv.split(",")));
        }

        return mergedRecipients.stream()
                .map(this::normalizeRecipient)
                .filter(value -> !value.isBlank())
                .distinct()
                .collect(Collectors.toList());
    }

    private String normalizeRecipient(String value) {
        if (value == null) {
            return "";
        }

        String normalized = value.trim();

        if (normalized.length() >= 2 && normalized.startsWith("\"") && normalized.endsWith("\"")) {
            normalized = normalized.substring(1, normalized.length() - 1).trim();
        }

        return normalized;
    }
}
