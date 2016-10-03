package com.db.sysgob.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.ProjectRepository;
import com.db.sysgob.test.BaseTest;

public class ProjectTest extends BaseTest {
	
	private static Logger log = Logger.getLogger("test");
	  
	  private ProjectRepository projectRepository;
	  private Project project;
	  
	  @Before
	  public void setup() {
		projectRepository = (ProjectRepository) applicationContext.getBean("projectRepository");
	    project = new Project();
	    
	    // Parameters for tests
	    project.setName("Proyecto de Prueba");
	    project.setDescription("Prueba 1");
	    project.setCreateDate((java.sql.Date) new Date());
	    project.setCategoryId(2L);
	    project.setBudgetId(1L);
	  }
	  
	  /* 
	   * Test:
	   * Check the connection with the database is alive 
	   */
	  @Test
	  public void healthCheck() {
		  String tmp = "";
			
		  try {
			URL url = new URL("http://localhost:3360/proyectos/probar_conexion");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

			while (null != (tmp = br.readLine())) {
				System.out.println(tmp);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	  }
	  
	  
	 /*
	  * Test:
	  * Create new project; persist in database
	  */
	  @Test
	  public void testInsert() {
	  
	    log.info("Test: persist project in DB");
	    log.info("Loading EntityManager mock");
	    
	    EntityManager entityManagerMock = mock(EntityManager.class);
	    
	    // Invoke class
	    projectRepository.setEntityManager(entityManagerMock);
	    projectRepository.createProject(project);
	    
	    // Validate flow
	    verify(entityManagerMock).persist(project);
	    log.info("Succesfully added user to the DB");
	  }
}
