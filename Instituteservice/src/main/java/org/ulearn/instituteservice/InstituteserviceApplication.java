package org.ulearn.instituteservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.ulearn.instituteservice.config.YMLConfig;

@SpringBootApplication
@ComponentScan(basePackages = { "org.ulearn.instituteservice.*" })
public class InstituteserviceApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstituteserviceApplication.class);

	@Autowired
	private YMLConfig myConfig;

	public static void main(String[] args) {
		SpringApplication.run(InstituteserviceApplication.class, args);
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
