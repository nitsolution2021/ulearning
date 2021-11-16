package org.ulearn.emailtemplateservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.ulearn.emailtemplateservice.config.YMLConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = { "org.ulearn.emailtemplateservice.*" })
@EnableSwagger2
public class EmailTemplateServiceApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateServiceApplication.class);

	@Autowired
	private YMLConfig myConfig;

	public static void main(String[] args) {
		SpringApplication.run(EmailTemplateServiceApplication.class, args);
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
