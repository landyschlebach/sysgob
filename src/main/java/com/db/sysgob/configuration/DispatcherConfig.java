package com.db.sysgob.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({ThymeleafConfig.class})
public class DispatcherConfig extends WebMvcConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(DispatcherConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	log.debug("Initializing ThymeleafConfig: Resources");
    	
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
