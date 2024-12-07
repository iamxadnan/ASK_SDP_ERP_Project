package com.klef.jfsd.sdpproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SdpprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdpprojectApplication.class, args);
		System.out.println("project is running");
	}

}
