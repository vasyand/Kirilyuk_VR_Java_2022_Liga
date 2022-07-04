package ru.homework.tasktracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner addGreeting() {
		return args -> System.out.println("Привет! Если не знаешь, что можно вводить, напиши help.");
	}

}
