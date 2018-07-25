package kr.zchat;

import org.junit.AfterClass;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.zchat.configuration.beans.AnnotationConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotationConfiguration.class)
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class BaseTestCase extends Assert {
	
	
	@AfterClass
	public static void testDown() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void dummy() {
	}
}
