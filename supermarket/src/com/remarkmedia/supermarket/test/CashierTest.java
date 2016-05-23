package com.remarkmedia.supermarket.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.remarkmedia.supermarket.main.Cashier;
import com.remarkmedia.supermarket.main.Customer;
import com.remarkmedia.supermarket.main.Good;
import com.remarkmedia.supermarket.main.Supermarket;
/**
 * Cashier Unit Test
 * @description 
 * @author ACE
 * @date 2016-5-19
 */
public class CashierTest {
	private static ScheduledExecutorService executor;
	@BeforeClass
	public static void testInitGoods(){		
		executor = Executors.newScheduledThreadPool(5);
	}
	@Test
	public void testHandleCustRequest(){
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.handleCustomer(cust);
		Assert.assertEquals(cashier.getCustSize(),1);
	}
	@Test
	public void testRun()throws Exception{
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.addCustomer(cust);
		cashier.run();
	}
	@Test
	public void getCustWaitTime(){
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.getCustTotalWait();
	}
	@Test
	public void getGoodWaitTime(){
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.getGoodTotalWait();
	}
	public void getCustSize(){
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.getCustTotalWait();
		cashier.getCustSize();
	}
}
