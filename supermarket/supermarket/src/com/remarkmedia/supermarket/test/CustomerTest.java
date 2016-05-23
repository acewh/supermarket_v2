package com.remarkmedia.supermarket.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import com.remarkmedia.supermarket.main.Customer;
import com.remarkmedia.supermarket.main.Good;
/**
 * Customer Unit Test
 * @description 
 * @author ACE
 * @date 2016-5-19
 */
public class CustomerTest {
	@Test
	public void testGetWaitTime(){
		Customer cust = new Customer("test");
		cust.setInitTime(System.currentTimeMillis());
		cust.setName("test1");
		cust.setGood(new Good("Apple"));
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cust.setHandleTime(System.currentTimeMillis());
		cust.getWaitTime();
		cust.getName();
		cust.getInitTime();
		cust.getName();
		cust.getGood();
	}
}
