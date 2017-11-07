package com.dragon.concurrent.number;

public class Number1 {
	public static synchronized void getOne() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println("one");
	}
	
	public static synchronized void getTwo() {
		System.out.println("two");
	}
//	
//	public  void getThree() {
//		System.out.println("three");
//	}
}
