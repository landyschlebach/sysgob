package com.db.sysgob.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.sysgob.entity.Budget;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.ProjectRepository;
import com.db.sysgob.test.BaseTest;

public class ProjectTest extends BaseTest {
	  private static Logger log = LoggerFactory.getLogger("sysgob_log");
	  
	  private ProjectRepository projectRepository;
	  private Project project;
	  
	  @Before
	  public void setup() {
		projectRepository = new ProjectRepository();
	    project = new Project();
	    
	    // Parameters for tests
	    project.setName("Proyecto de Prueba");
	    project.setDescription("Prueba 1");
	    project.setCreateDate((java.sql.Timestamp) new Date());
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
	  
	  @Test
	  public void testDatosCompletos() {
		  boolean res = false;
		  
		 Project projectTest = new Project();
		  project.setName("prueba");
		  project.setDescription("esto es una prueba");
		  project.setAmount(1000L);
		  project.setCategoryId(1L);
		
	    res = testDatos(projectTest);
	                           
	    assertEquals(true, res);
	  }
	  
	  @Test
	  public void testDatosIncompletos() {
		  boolean res = false;
		  
		 Project projectTest = new Project();
		  project.setName("prueba");
		  project.setDescription("esto es una prueba");
		  project.setCategoryId(1L);
		
	    res = testDatos(projectTest);
	                           
	    assertEquals(false, res);
	  }


	  public boolean testDatos(Project project){
				
			if(project.getName() != null && project.getAmount() != null && project.getCategoryId()!= null){
				return true;
			}
			return false;
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
