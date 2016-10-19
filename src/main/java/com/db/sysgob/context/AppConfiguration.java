package com.db.sysgob.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.db.sysgob.configuration.DispatcherConfig;
import com.db.sysgob.configuration.PersistenceConfig;

@Configuration
@ComponentScan(basePackages = {"com.db.sysgob.bo", 
		"com.db.sysgob.controller", 
		"com.db.sysgob.entity",
		"com.db.sysgob.repository",
		"com.db.sysgob.service",
		"com.db.sysgob.context",
		"com.db.sysgob.configuration"})
@Import({ WebInitializer.class, DispatcherConfig.class, PersistenceConfig.class})
public class AppConfiguration {

}
