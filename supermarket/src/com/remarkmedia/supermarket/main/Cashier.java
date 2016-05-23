package com.remarkmedia.supermarket.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
	private BlockingQueue<Customer> customerLine;
	/**
	 * the supermarket which cashier belongs to
	 */
	private final Supermarket supermarket;
	private final ScheduledExecutorService executorService;
	public Cashier(String _name,Supermarket _supermarket,ScheduledExecutorService _executorService){
		receiveList = new ArrayList<Customer>();
		customerLine = new LinkedBlockingQueue<Customer>();
		this.executorService = _executorService;
		this.supermarket=_supermarket;
		this.name = _name;
	}
	public List<Customer> getReceiveList() {
		return receiveList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public BlockingQueue<Customer> getCustomerLine() {
		return customerLine;
	}
	public void setCustomerLine(BlockingQueue<Customer> customerLine) {
		this.customerLine = customerLine;
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
		return supermarket.isSoldOut&&customerLine.size()==0;
	}
}
