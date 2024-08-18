package com.meowmentor.themeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class ThemeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThemeServiceApplication.class, args);
	}

}
