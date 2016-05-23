package com.remarkmedia.supermarket.main;
/**
 * GOOD
 * @description 
 * @author ACE
 * @date 2016-5-16
 */
public class Good {
	private String name;
	private long initTime;
	private long sellTime;
	public Good(){
		
	}	
	public Good(String _name){
		this.name=_name;		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getInitTime() {
		return initTime;
	}
	/**
	 * set the init time
	 * @param initTime
	 */
	public void setInitTime(long initTime) {
		this.initTime = initTime;
	}
	public long getSellTime() {
		return sellTime;
	}
	public void setSellTime(long sellTime) {
		this.sellTime = sellTime;
	}
	public long getSoldTime(){
		return sellTime-initTime;
	}
}
