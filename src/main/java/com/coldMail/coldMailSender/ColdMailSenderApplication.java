package com.coldMail.coldMailSender;

import com.coldMail.coldMailSender.model.EmailTemplate;
import com.coldMail.coldMailSender.service.EmailService;
import com.coldMail.coldMailSender.service.TemplateService;
import com.coldMail.coldMailSender.util.RecipientLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColdMailSenderApplication {

	private static final Logger LOGGER = Logger.getLogger(ColdMailSenderApplication.class.getName());

	public static void main(String[] args) {
		try {

			TemplateService templateService = new TemplateService();
			EmailService emailService = new EmailService();

			EmailTemplate template =
					templateService.loadTemplate("coldmail.xml");

			for (String recipient : RecipientLoader.loadRecipients()) {

				String personalizedBody =
						templateService.personalize(
								template.getBody(),
								recipient
						);

				emailService.sendEmail(
						recipient,
						template.getSubject(),
						personalizedBody
				);

				System.out.println("Sent to: " + recipient);

				Thread.sleep(4000); // 4 sec delay to avoid spam flag
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error sending emails", e);
		}
	}

}
