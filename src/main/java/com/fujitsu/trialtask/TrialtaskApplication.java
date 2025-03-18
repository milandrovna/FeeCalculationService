package com.fujitsu.trialtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrialtaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrialtaskApplication.class, args);
	}
}
