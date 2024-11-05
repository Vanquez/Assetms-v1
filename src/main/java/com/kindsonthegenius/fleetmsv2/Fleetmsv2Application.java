package com.kindsonthegenius.fleetmsv2;

import com.kindsonthegenius.fleetmsv2.mail.services.EmailService;
import com.kindsonthegenius.fleetmsv2.security.SpringSecurityAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Fleetmsv2Application extends SpringBootServletInitializer {

	@Autowired
	private EmailService senderService;

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(Fleetmsv2Application.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		senderService.sendEmail("toemail@gmail.com",
//				"This is email body",
//				"This is email subject");
//
//	}

}
