package com.remarkmedia.supermarket.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.BeforeClass;
import org.junit.Test;
import com.remarkmedia.supermarket.main.CustomerGenerator;
import com.remarkmedia.supermarket.main.Supermarket;
/**
 * 
 * @description Customer Generator Unit Test 
 * @author ACE
 * @date 2016-5-19
 */
public class CustomerGerneratorTest {
	private static Supermarket supermarket;
	private static ScheduledExecutorService executor;	
	@BeforeClass
	public static void testInitGoods(){
		supermarket = new Supermarket("onepiece");
		supermarket.initSupermarket();	
		executor = Executors.newScheduledThreadPool(5);
	}
	@Test
	public void testRun(){
		CustomerGenerator generator = new CustomerGenerator(supermarket,executor);
		generator.run();
	}
}
