package com.db.sysgob;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.db.sysgob.configuration.DispatcherConfig;
import com.db.sysgob.configuration.PersistenceConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.db.sysgob")
@Import({ WebInitializer.class, DispatcherConfig.class, PersistenceConfig.class})
public class AppConfiguration {

}
