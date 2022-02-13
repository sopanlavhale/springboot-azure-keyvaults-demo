package com.azure.keyvaluts;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KeyvalutsApplication {
  private static final Logger LOG = LogManager.getLogger(KeyvalutsApplication.class);
  
	public static void main(String[] args) {
		SpringApplication.run(KeyvalutsApplication.class, args);
	}
	  @Bean
	  public CommandLineRunner run(ApplicationContext appContext) {
	    return args -> {

	      String[] beans = appContext.getBeanDefinitionNames();
	      Arrays.stream(beans).sorted().forEach(LOG::trace);
	    };
	  }

}
