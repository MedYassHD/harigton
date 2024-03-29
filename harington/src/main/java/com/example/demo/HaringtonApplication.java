package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.service.BarchServiceImpl;

@SpringBootApplication
@ComponentScan("com.example.demo")
public class HaringtonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaringtonApplication.class, args);
	}

}
