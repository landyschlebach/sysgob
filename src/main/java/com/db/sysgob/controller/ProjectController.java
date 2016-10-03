package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.db.sysgob.repository.ProjectRepository;
import com.db.sysgob.repository.ResourcesByProjectRepository;

@Controller
@RequestMapping("proyectos")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ResourcesByProjectRepository rpRepository;
	
	@RequestMapping("nuevo")
	public String form() {
		return "";
	}

	@RequestMapping("nuevo?nombre=&descripcion=&categoria=")
	public String newProject() {
		return "";
	}	
	
	@RequestMapping("probar_conexion")
	public String checkConnection() {
		return projectRepository.healthcheck();
	}
}
