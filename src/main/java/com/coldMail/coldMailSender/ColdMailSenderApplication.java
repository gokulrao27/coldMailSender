package com.coldMail.coldMailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ColdMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColdMailSenderApplication.class, args);
	}

}
