/**
 * 
 */
package com.remarkmedia.supermarket.test;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.remarkmedia.supermarket.main.Supermarket;


/**
 * 
 * @description Supermarket Unit Test 
 * @author ACE
 * @date 2016-5-19
 */
public class SupermarketTest {
	private static Supermarket supermarket;
	@BeforeClass
	public static void testInitGoods(){
		supermarket = new Supermarket("onepiece");
		supermarket.initGoods("Apple",15);
		supermarket.initGoods("Macbook",15);
		supermarket.initGoods("Cookie",15);
	}
	@Test
	public void testGetRandomGood(){
		for(int i =0;i<45;i++){
			supermarket.getRandomGood();
		}
		Assert.assertTrue(supermarket.getRandomGood()==null);			
	}
	@Test 
	public void testStart(){
		Supermarket market = new Supermarket("onepiece");
		market.start();
	}
	@Test 
	public void testStatInfo()throws Exception{
		Supermarket market = new Supermarket("onepiece");
		market.initSupermarket();
		market.statInfo();
	}
	@Test
	public void testInit(){
		Supermarket market = new Supermarket("onepiece");
		market.initSupermarket();
	}
	@Test
	public void testGetCustomerDelay(){
		Supermarket market = new Supermarket("onepiece");
		market.getCustomerDelay();
	}
	@Test
	public void testGetCashierDelay(){
		Supermarket market = new Supermarket("onepiece");
		market.getCashierDelay();
	}
}
