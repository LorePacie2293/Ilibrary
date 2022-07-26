package com.Ilibrary.BooksService.Config;

import java.util.Locale;

import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//configura la sorgente dei messaggi (file propieties)
@Configuration
public class MessageConfig {
	
	//Imposta la sorgenete degli errori
	@Bean(name="Validator")
	public LocalValidatorFactoryBean validator() {
		
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	//Imposta la sorgente dei messaggi
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("message");
		resource.setUseCodeAsDefaultMessage(true);
		return resource;
	
	}
	
	//Imposta lingua di DEFAULT
	@Bean
	public SessionLocaleResolver localResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale("it"));
		return localResolver;
	}
}
