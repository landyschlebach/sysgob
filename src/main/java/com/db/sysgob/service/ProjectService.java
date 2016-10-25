package com.db.sysgob.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Project;
import com.db.sysgob.entity.ProjectDetail;
import com.db.sysgob.entity.ProjectSummary;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.repository.ProjectRepository;

@Service
public class ProjectService {
	private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public boolean create(Project project){
		log.debug("WebService [CREATE]");
		boolean result = false;
		
		try {
			projectRepository.createProject(project);
			result = true;

			log.debug("Successfully created Project [" + project +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean modify(Project project){
		log.debug("WebService [UPDATE]");
		boolean result = false;
		
		try {
			projectRepository.updateProject(project);
			result = true;

			log.debug("Successfully updated Project [" + project +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public boolean remove(Project project){
		log.debug("WebService [DELETE]");
		boolean result = false;
		
		try {
			projectRepository.deleteProject(project);
			result = true;

			log.debug("Successfully deleted Project [" + project +"]");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<ProjectSummary> findByDependencyId(Long dependencyId){
		List<ProjectSummary> result = null;
		
		try {
			result = projectRepository.getProjectSummariesByDependency(dependencyId);
			log.debug("Result of " + result);
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<ProjectDetail> findProjectDetailsByDependencyId(Long dependencyId){
		List<ProjectDetail> result = null;
		
		try {
			result = projectRepository.getProjectsDetailByDependency(dependencyId);
			log.debug("Result of " + result);
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}	
	
	public Project findById(Long id){
		Project result = null;
		
		try {
			result = projectRepository.getById(id);
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<Project> findAll(){
		List<Project> result = null;
		
		try {
			result = projectRepository.getProjects();
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public List<Category> getProjectCategories(){
		List<Category> result = null;
		
		try {
			result = categoryRepository.getCategories();
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		
		return result;
	}
}
