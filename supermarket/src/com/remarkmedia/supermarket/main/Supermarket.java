package com.remarkmedia.supermarket.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @description Supermarket 
 * @author ACE 
 * @date 2016-5-16
 */
public class Supermarket implements Runnable{
	private Logger logger = Logger.getLogger(Supermarket.class);
	private String name;
	private long startBusinessTime;
	private int statInterval=5;//business statistic interval default value 5
	public Supermarket(String _name){
		this.name = _name;
	}
	/**
	 * goods repository
	 */
	private Map<String,LinkedList<Good>> repository = new HashMap<String, LinkedList<Good>>();
	/**
	 * the cashier list of the supermarket
	 */
	private List<Cashier> cashierList;		
	/**
	 * the main Executor of the Supermarket simulation
	 */
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
	private Random rand = new Random();
	private volatile boolean isSoldOut = false;
	/**
	 * customer index
	 */
	private int customerIndex;
	/**
	 * start the supermarket
	 */
	public void start(){
		// TODO Auto-generated method stub
		initSupermarket();
		//start business
		startBusinessTime = System.currentTimeMillis();				
		generateCustomer();		
		for(Cashier cashier:cashierList){
			executor.schedule(cashier,getCashierDelay(),TimeUnit.SECONDS);
		}
		executor.schedule(this,statInterval,TimeUnit.SECONDS);
	}
	/**
	 * initialize the supermarket
	 */
	public void initSupermarket(){
		logger.info(name+"    started!!!!");
		logger.info("begin to init the cashiers!!!");
		cashierList = new ArrayList<Cashier>();
		Cashier cashierA = new Cashier("Cashier_A",this,executor);
		Cashier cashierB = new Cashier("Cashier_B",this,executor);
		Cashier cashierC = new Cashier("Cashier_C",this,executor);		
		cashierList.add(cashierA);
		cashierList.add(cashierB);
		cashierList.add(cashierC);
		logger.info("init the cashiers over!!!");
		//init goods
		logger.info("begin to init the goods!!!");
		initGoods("Apple",15);
		initGoods("Macbook",15);
		initGoods("Cookie",15);
		logger.info("init the goods over!!!");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!isClose()){
			//print the statistics			
			statInfo();
			executor.schedule(this,statInterval,TimeUnit.SECONDS);
		}else{
			long endBusinessTime = System.currentTimeMillis();
			logger.info("The Total Business Time of Supermarket is :"+(endBusinessTime-startBusinessTime)/1000+" Seconds!");			
			statInfo();
			executor.shutdown();
		}
	}	
	/**
	 * generate customer in 1 or 3 second
	 */
	private void generateCustomer(){
		executor.schedule(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Good good = getRandomGood();
				if(good!=null){
					customerIndex++;
					Customer cust = new Customer("Customer"+customerIndex);			
					cust.setInitTime(System.currentTimeMillis());//set the start waiting time
					cust.setGood(good);
					getRandomCashier().addCustomer(cust);
					logger.info(cust.getName()+" coming!!,buys a:"+cust.getGood().getName());
					executor.schedule(this, getCustomerDelay(),TimeUnit.SECONDS);
				}
			}
		}, getCustomerDelay(),TimeUnit.SECONDS);
	}
	/**
	 * init goods
	 * @param name name of goods
	 * @param count 
	 */
	public void initGoods(String name,long count){
		LinkedList<Good> list = new LinkedList<Good>();
		repository.put(name,list);		
		for(int i=1;i<=count;i++){
			Good good = new Good(name);
			//set the time when check in 
			good.setInitTime(System.currentTimeMillis());
			list.add(good);
		}
		logger.info("init "+name+",count:"+count+" success!!!");
	}
	/**
	 * get a good randomly
	 * @return
	 */
	public Good getRandomGood(){
		List<String> remainGoods = new ArrayList<String>();
		for(String key:repository.keySet()){
			List<Good> goodList = repository.get(key);
			if(goodList.size()!=0){
				remainGoods.add(key);
			}
		}
		if(remainGoods.size()==0){
			logger.info("all of the goods sold out !!!!!");
			isSoldOut=true;			
			return null;
		}else if(remainGoods.size()==1){
			String goodName = remainGoods.get(0);
			return repository.get(goodName).pollFirst(); 
		}else{
			Random rand = new Random();
			int seed = rand.nextInt(remainGoods.size());
			String goodName = remainGoods.get(seed);
			return repository.get(goodName).pollFirst();
		}
	}	
	/**
	 * customer wait in line randomly
	 * @return
	 */
	private Cashier getRandomCashier(){
		int randomIndex = rand.nextInt(cashierList.size());
		return cashierList.get(randomIndex);
	}
	/**
	 * stat business info
	 */
	public void statInfo(){
		long custWait = 0;
		long goodWait = 0;
		int custSize = 0;
		for(Cashier cashier:cashierList){
			custSize +=cashier.getCustSize();
			logger.info("The Number of Customers "+cashier.getName()+" Received is:"+cashier.getCustSize());
			custWait+=cashier.getCustTotalWait();
			goodWait+=cashier.getGoodTotalWait();
		}		
		if(custSize!=0){
			logger.info("The Average Wait Time Of Each Customer:"+custWait/custSize/1000+" Seconds");
			logger.info("The Average Sold Time Of Each Good:"+goodWait/custSize/1000+" Seconds");
		}						
	}	
	/**
	 * get the customer's delay time
	 * @return
	 */
	public int getCustomerDelay(){
		int delay = rand.nextInt(3)+1;
		return delay;
	}
	/**
	 * get the cashier's delay time
	 * @return
	 */
	public int getCashierDelay(){
		int delay = rand.nextInt(6)+5;
		return delay;
	}
	public boolean isSoldOut(){
		return isSoldOut;
	}
	/**
	 * judge whether supermarket is closed
	 * @return
	 */
	public boolean isClose(){
		boolean isClose = true;
		for(Cashier cashier:cashierList){
			if(!cashier.isFinish()){
				isClose = false;
				break;
			}
		}
		return isClose;
	}		
}
