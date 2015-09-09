package fr.capverszero.recunit.aspect;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recunit.test.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.capverszero.recunit.Mode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springContext.xml" })
public class RecUnitPointCutTest {
	@Autowired
	Service service;
	
	@Autowired
	RecUnitPointCut recUnit;
	
	@Test
	public void testRecordeAndPlay(){
		recUnit.setIdentifier("testRecordeAndPlay");
		recUnit.setMode(Mode.Record);
		
		int result1 = service.calculateService(10);
		
		recUnit.initCounters();
	    recUnit.setMode(Mode.Play);
		
		int result2 = service.calculateService(10);
		
		assertEquals(result1, result2);
	}

}
