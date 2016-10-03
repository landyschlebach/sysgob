package com.db.sysgob.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration(value="classpath:/app-context.xml")
public class BaseTest {
	
	@Autowired	
	protected ApplicationContext applicationContext;

	@Test
	/* 
	 * Test: 
	 * Valid properties for database connection 
	 */
	public void test() {
		Assert.assertNotNull(applicationContext);
	}
}
