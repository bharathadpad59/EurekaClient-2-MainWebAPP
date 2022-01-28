package com.example.webapp.configuration;

import java.util.Locale;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.google.gson.GsonBuilder;

@Configuration
@RefreshScope
public class BeanConfiguration {

	
	//we can configures gson bean in 2 ways 1 from application.properties and another like this from @configuration class creating the 
	//bean and provideing the custom Bean with logic so that one will be use all over with @Autowired GsonBuilder gsonBuilder;
	//and when we aree creating a GsonBuilder bean from here it will go to spring beans factory and same configuration will be apply
	//to all the rest apis response  eg. in prop file now null is exluded works fine then i created @Bean of gsonBuyilder with null included
	// now in response null values came // comment this to see the response without null values 
	@Bean
	GsonBuilder gsonBuilder(){
	    GsonBuilder gsonBuilder=new GsonBuilder();
	    gsonBuilder.serializeNulls();
	    return gsonBuilder;
	}
	
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder getwebClientBuilder(){
		return WebClient.builder();
	}
	
//	@Bean
//	public LocaleResolver localeResolver() {
//	   SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//	   sessionLocaleResolver.setDefaultLocale(Locale.US);
//	   return sessionLocaleResolver;
//	}
//	
//	 @Bean
//	   public LocaleChangeInterceptor localeChangeInterceptor() {
//	      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//	      localeChangeInterceptor.setParamName("language");
//	      return localeChangeInterceptor;
//	   }
	 
	 @Bean
		public ResourceBundleMessageSource messageSourcetest() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasename("messages");
			messageSource.setDefaultLocale(Locale.US);
			return messageSource;
		}
	 
}
