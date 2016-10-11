package com.db.sysgob.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.db.sysgob.entity.Expense;
import com.db.sysgob.repository.ExpenseRepository;

public class ExpenseTest extends BaseTest {
	
	private static Logger log = Logger.getLogger("test");
	  
	  private ExpenseRepository expenseRepository;
	  private Expense expense;
	  
	  @Before
	  public void setup() {
		expenseRepository = (ExpenseRepository) applicationContext.getBean("expenseRepository");
		expense = new Expense();
	    
	    // Parameters for tests
	    expense.setName("Gasto de Prueba");
	    expense.setTotalAmount(100L);
	    expense.setCreateDate((java.sql.Date) new Date());
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
}
