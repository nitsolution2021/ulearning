package org.ulearn.packageservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.ulearn.packageservice.config.YMLConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages= {"org.ulearn.packageservice.*"})
@EnableSwagger2
@EnableEurekaClient
public class PackageserviceApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(PackageserviceApplication.class);
	
	@Autowired
	private YMLConfig myConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(PackageserviceApplication.class, args);
		LOGGER.info("In PackageserviceApplication Main Method");
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
		
	}

}
