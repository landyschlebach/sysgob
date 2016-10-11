package com.db.sysgob.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.sysgob.entity.Category;
import com.db.sysgob.entity.Classification;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.CategoryRepository;
import com.db.sysgob.repository.ProjectRepository;

@Component
public class ProjectBO {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Long calculateCategory(Classification classification) {
		Long total = classification.getRiskClassification() + 
				classification.getOthersClassification() + 
				classification.getFinanceClassification() + 
				classification.getOtherImplications() + 
				classification.getStrategicClassification();
		
		return total;
	}
	
	public Project defineCategory(Project project, Long points) {
		Long systemCategoryId = 0L;
		Category category = categoryRepository.getById(project.getCategoryId());
		Long averageCategory = (category.getMinScore() + points) / 2;
		
		systemCategoryId = categoryRepository.getCategoryByPoints(averageCategory);
		project.setCategoryId(systemCategoryId);
		
		return project;
	}
}
