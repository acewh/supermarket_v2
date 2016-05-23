/**
 * 
 */
package com.remarkmedia.supermarket.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.remarkmedia.supermarket.main.Cashier;
import com.remarkmedia.supermarket.main.Customer;
import com.remarkmedia.supermarket.main.Good;
import com.remarkmedia.supermarket.main.Supermarket;


/**
 * 
 * @description Supermarket Unit Test 
 * @author ACE
 * @date 2016-5-19
 */
public class SupermarketTest {
	private static Supermarket supermarket;
	private static ScheduledExecutorService executor;
	@BeforeClass
	public static void testInitGoods(){
		supermarket = new Supermarket("onepiece");
		supermarket.initGoods("Apple",15);
		supermarket.initGoods("Macbook",15);
		supermarket.initGoods("Cookie",15);
		executor = Executors.newScheduledThreadPool(5);
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
		Cashier cashier = new Cashier("testCasiher",market,executor);
		Customer cust = new Customer("testCust");
		Good good = new Good("Apple");
		good.setInitTime(System.currentTimeMillis());
		cust.setInitTime(System.currentTimeMillis());
		TimeUnit.SECONDS.sleep(1);
		cust.setHandleTime(System.currentTimeMillis());
		TimeUnit.SECONDS.sleep(1);
		good.setSellTime(System.currentTimeMillis());
		cust.setGood(good);
		cashier.getReceiveList().add(cust);
		List<Cashier> list = new ArrayList<Cashier>();
		list.add(cashier);
		market.setCashierList(list);
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
