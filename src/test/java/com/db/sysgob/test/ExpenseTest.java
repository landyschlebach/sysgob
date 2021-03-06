package com.db.sysgob.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.sysgob.entity.Expense;
import com.db.sysgob.entity.Project;
import com.db.sysgob.repository.ExpenseRepository;

public class ExpenseTest {
	  private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	  private ExpenseRepository expenseRepository;
	  private Expense expense;
	  
	  @Before
	  public void setup() {
		expenseRepository = new ExpenseRepository();
		expense = new Expense();
	    
	    // Parameters for tests
	    expense.setName("Gasto de Prueba");
	    expense.setTotalAmount(100L);
	    expense.setCreateDate((java.sql.Timestamp) new Date());
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
	  * Create new expense; persist in database
	  */
	  @Test
	  public void testInsert() {
	  
	    log.info("Test: persist expense in DB");
	    log.info("Loading EntityManager mock");
	    
	    EntityManager entityManagerMock = mock(EntityManager.class);
	    
	    // Invoke class
	    expenseRepository.setEntityManager(entityManagerMock);
	    expenseRepository.createExpense(expense);
	    
	    // Validate flow
	    verify(entityManagerMock).persist(expense);
	    log.info("Succesfully added user to the DB");
	  }
	  
	  @Test
	  public void testDatosCompletos() {
		  boolean res = false;
		  
		 Expense expenseTest = new Expense();
		  expense.setName("gasto prueba");
		  expense.setTotalAmount(2000L);
		
	    res = testDatos(expenseTest);
	                           
	    assertEquals(true, res);
	  }
	  
	  @Test
	  public void testDatosIncompletos() {
		  boolean res = false;
		  
		 Expense expenseTest = new Expense();
		  expense.setName("gasto prueba");
		
	    res = testDatos(expenseTest);
	                           
	    assertEquals(false, res);
	  }

	  public boolean testDatos(Expense expense){
			
			if(expense.getName() != null && expense.getTotalAmount() != null ){
				return true;
			}
			return false;
	   }
	  
}
