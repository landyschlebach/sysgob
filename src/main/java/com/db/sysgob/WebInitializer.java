package com.db.sysgob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.db.sysgob.configuration.DispatcherConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
public class WebInitializer implements WebApplicationInitializer {
	private static final Logger log = LoggerFactory.getLogger(WebInitializer.class);
	
    @Override
    public void onStartup(ServletContext container) throws ServletException {
    	log.debug("Initializing SYSGOB");
    	
    	log.debug("Creating the 'root' Spring application context");
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfiguration.class);

    	log.debug("Manage the lifecycle of the root application context");
        container.addListener(new ContextLoaderListener(rootContext));

    	log.debug("Create the dispatcher servlet's Spring application context");
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(DispatcherConfig.class);

    	log.debug("Register and map the dispatcher servlet");
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
