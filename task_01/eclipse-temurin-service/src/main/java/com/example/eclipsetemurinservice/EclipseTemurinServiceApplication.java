package com.example.eclipsetemurinservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
public class EclipseTemurinServiceApplication {
	@RequestMapping("/")
	public String home() {
	  return "Hello Backend Software Developer Technical Assessment";
	}

	public static void main(String[] args) {
		SpringApplication.run(EclipseTemurinServiceApplication.class, args);
	}

}
