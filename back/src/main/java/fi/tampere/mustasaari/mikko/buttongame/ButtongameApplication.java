package fi.tampere.mustasaari.mikko.buttongame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class ButtongameApplication {

	public static void main(String[] args) {
		SpringApplication.run(ButtongameApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/create-user").allowedOrigins("*");
				registry.addMapping("/roll/*").allowedOrigins("*");
				registry.addMapping("/credits/*").allowedOrigins("*");
			}
		};
	}
}