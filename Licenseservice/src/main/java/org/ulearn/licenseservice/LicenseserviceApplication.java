package org.ulearn.licenseservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.ulearn.licenseservice.config.YMLConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"org.ulearn.licenseservice.*"})
public class LicenseserviceApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseserviceApplication.class);
	
	@Autowired
	private YMLConfig myConfig;
	
	
	public static void main(String[] args) {
		SpringApplication.run(LicenseserviceApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : " + myConfig.getName());
		LOGGER.info("environment : " + myConfig.getEnvironment());
		LOGGER.info("contextpath : " + myConfig.getContextpath());
		LOGGER.info("servers: " + myConfig.getServers());
		LOGGER.info("-------------------------------------");
		
	}

}
