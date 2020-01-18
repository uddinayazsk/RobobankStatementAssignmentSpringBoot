package com.cognizant.BankStatement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cognizant.BankStatement.service.*;

/**
 * @author Adil
 *
 */
@Configuration
public class AppConfig {
    @Bean
    public ExtractorServiceImpl transferService() {
        return new ExtractorServiceImpl();
    }
    @Bean
    public ValidatorServiceImpl validService(){
    	return new ValidatorServiceImpl();
    }
}