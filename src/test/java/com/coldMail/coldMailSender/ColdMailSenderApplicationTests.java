package com.coldMail.coldMailSender;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "cold-mail.enabled=false")
class ColdMailSenderApplicationTests {

	@Test
	void contextLoads() {
	}

}
