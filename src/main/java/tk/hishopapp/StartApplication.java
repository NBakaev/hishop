package tk.hishopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("tk.hishopapp")
@Configuration
@EnableAutoConfiguration
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
}