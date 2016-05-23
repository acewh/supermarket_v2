package com.remarkmedia.supermarket.test;

import org.junit.Test;
import com.remarkmedia.supermarket.main.Good;
/**
 * Good Unit Test
 * @description 
 * @author ACE
 * @date 2016-5-19
 */
public class GoodTest {
	@Test
	public void goodTest(){
		Good good = new Good("Apple");
		good.setInitTime(System.currentTimeMillis());
		good.setName("Cookie");
		good.setSellTime(System.currentTimeMillis());
		good.getInitTime();
		good.getName();
		good.getSellTime();
		good.getSoldTime();
	}
}
