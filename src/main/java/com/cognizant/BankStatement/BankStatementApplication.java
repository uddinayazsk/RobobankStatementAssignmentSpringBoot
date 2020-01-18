package com.cognizant.BankStatement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Adil
 *
 */

@SpringBootApplication(scanBasePackages={"com.cognizant.BankStatement.*"})
//@EnableConfigurationProperties(ConfigProperties.class)
public class BankStatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankStatementApplication.class, args);
	}

}
