package com.db.sysgob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public boolean create(Project project){
		boolean result = false;
		
		try {
			projectRepository.createProject(project);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean modify(Project project){
		boolean result = false;
		
		try {
			projectRepository.updateProject(project);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean remove(Project project){
		boolean result = false;
		
		try {
			projectRepository.deleteProject(project);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public List<Project> findById(Long id){
		List<Project> result = null;
		
		try {
			result = projectRepository.getProjects(id);
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public List<Project> search(){
		List<Project> result = null;
		
		try {
			result = projectRepository.getProjects();
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
}
