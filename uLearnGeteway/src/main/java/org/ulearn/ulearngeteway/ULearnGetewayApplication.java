package org.ulearn.ulearngeteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ULearnGetewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ULearnGetewayApplication.class, args);
	}

}
