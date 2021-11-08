package com.example.webapp.filterAndInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class HandlerInterceptorAppConfig implements  WebMvcConfigurer  {

	@Autowired
	RequestAndResponseHandler handler;
	
//	@Autowired
//	LocaleChangeInterceptor localeChangeInterceptor;
	
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(handler);
//	      registry.addInterceptor(localeChangeInterceptor);
	   }
	   
	   
}
