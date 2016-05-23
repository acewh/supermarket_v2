package com.remarkmedia.supermarket.main;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
/**
 * customer generator task
 * @description create a customer in 1-3 second 
 * @author ACE
 * @date 2016-5-17
 */
public class CustomerGenerator implements Runnable{
	private Logger logger = Logger.getLogger(CustomerGenerator.class);
	/**
	 * supermarket where the customers go to
	 */
	private final Supermarket supermarket;	
	private final ScheduledExecutorService executorService;
	public CustomerGenerator(Supermarket _supermarket,ScheduledExecutorService _executorService){
		this.supermarket = _supermarket;
		this.executorService = _executorService;
	}	
	private int customerIndex;
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		if(!supermarket.isSoldOut){
			Customer cust = generateCustomer();
			if(cust!=null){
				try {
					getRandomCashier().getCustomerLine().put(cust);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				executorService.schedule(this, supermarket.getCustomerDelay(),TimeUnit.SECONDS);
			}
		}
	}
	/**
	 * generate a customer
	 * @return
	 */
	public Customer generateCustomer(){
		Customer cust = null;
		Good good = supermarket.getRandomGood();
		if(good!=null){
			customerIndex++;
			cust = new Customer("Customer"+customerIndex);			
			cust.setInitTime(System.currentTimeMillis());//set the start waiting time
			cust.setGood(good);
			logger.info(cust.getName()+" coming!!,buys a:"+cust.getGood().getName());				
		}
		return cust;
	}
	/**
	 * customer wait in line randomly
	 * @return
	 */
	private Cashier getRandomCashier(){
		Random rand = new Random();
		int randomIndex = rand.nextInt(supermarket.getCashierList().size());
		return supermarket.getCashierList().get(randomIndex);
	}
}
