package com.db.sysgob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.db.sysgob.repository.ProjectRepository;

@Service
public class ConnectionService {
	private static final Logger log = LoggerFactory.getLogger(ConnectionService.class);
	
	@RequestMapping("/probar_conexion")
	public String checkConnection() {
		log.debug("Checking DB connectivity");
		ProjectRepository projectRepository = new ProjectRepository();

		return projectRepository.healthcheck();
	}
}
