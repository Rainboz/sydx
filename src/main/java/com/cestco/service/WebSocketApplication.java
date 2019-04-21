package com.cestco.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class WebSocketApplication {
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(WebSocketApplication.class, args);
	 }
}