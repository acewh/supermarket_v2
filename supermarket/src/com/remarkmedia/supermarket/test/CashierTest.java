package com.remarkmedia.supermarket.test;

import java.util.concurrent.CountDownLatch;
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
		Assert.assertEquals(cashier.getReceiveList().size(),1);
	}
	@Test
	public void testRun()throws Exception{
		Supermarket supermarket = new Supermarket("test");
		Customer cust = new Customer("testCust");
		cust.setGood(new Good("Apple"));
		supermarket.setSoldOut(true);
		CountDownLatch latch = new CountDownLatch(1);
		Cashier cashier = new Cashier("testCashier",supermarket,executor);
		cashier.run();
	}
}
