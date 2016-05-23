package com.remarkmedia.supermarket.main;
/**
 * 
 * @description Customer of supermarket 
 * @author ACE
 * @date 2016-5-16
 */
public class Customer {		
	private String name;
	//Customer can only buy one good
	private Good good;
	private long initTime;
	private long handleTime;	
	public Customer(String _name){
		this.name = _name;		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Good getGood() {
		return good;
	}
	public void setGood(Good good) {
		this.good = good;
	}
	public long getInitTime() {
		return initTime;
	}
	public void setInitTime(long initTime) {
		this.initTime = initTime;
	}
	public long getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(long handleTime) {
		this.handleTime = handleTime;
	}
	public long getWaitTime(){
		return handleTime-initTime;
	}
}
