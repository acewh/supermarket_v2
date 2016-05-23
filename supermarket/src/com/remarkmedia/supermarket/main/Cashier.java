package com.remarkmedia.supermarket.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @description handle a Customer Request in 5-10 seconds   
 * @author ACE
 * @date 2016-5-16
 */
public class Cashier implements Runnable{
	private Logger logger = Logger.getLogger(Cashier.class);
	private String name;
	/**
	 * customers received by this cashier
	 */
	private List<Customer> receiveList;
	/**
	 * customer line of the cashier(each cashier holds a line can reduece race)
	 */
	private ConcurrentLinkedQueue<Customer> customerLine;
	/**
	 * the supermarket which cashier belongs to
	 */
	private final Supermarket supermarket;
	private final ScheduledExecutorService executorService;
	public Cashier(String _name,Supermarket _supermarket,ScheduledExecutorService _executorService){
		receiveList = new ArrayList<Customer>();
		customerLine = new ConcurrentLinkedQueue<Customer>();
		this.executorService = _executorService;
		this.supermarket=_supermarket;
		this.name = _name;
	}	
	public String getName() {
		return name;
	}
	public void addCustomer(Customer customer){	
		customerLine.offer(customer);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub	
		if(!isFinish()){
			Customer customer = customerLine.poll();
			if(customer!=null){
				handleCustomer(customer);
			}
			int delay = supermarket.getCashierDelay();
			executorService.schedule(this, delay, TimeUnit.SECONDS);
		}
	}
	/**
	 * handle a customer request
	 * @param cust
	 */
	public void handleCustomer(Customer cust){
		cust.getGood().setSellTime(System.currentTimeMillis());
		cust.setHandleTime(System.currentTimeMillis());		
		receiveList.add(cust);
		logger.info(name+" Handle The "+cust.getName()+"'s Request !");
	}
	/**
	 * judge duty
	 * @return
	 */
	public boolean isFinish(){
		return supermarket.isSoldOut()&&customerLine.isEmpty();
	}
	/**
	 * get customer total wait time
	 * @return
	 */
	public long getCustTotalWait(){
		long custWait = 0;
		for(Customer cust:receiveList){
			custWait+=cust.getWaitTime();			
		}
		return custWait;
	}
	/**
	 * get good total sold time
	 * @return
	 */
	public long getGoodTotalWait(){
		long goodWait = 0;
		for(Customer cust:receiveList){
			goodWait+=cust.getGood().getSoldTime();
		}
		return goodWait;
	}
	/**
	 * get size of receiveList 
	 * @return
	 */
	public int getCustSize(){
		return receiveList.size();
	}
}
