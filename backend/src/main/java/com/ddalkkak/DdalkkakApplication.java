package com.ddalkkak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DdalkkakApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdalkkakApplication.class, args);
	}

}
