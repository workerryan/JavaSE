package com.dragon.se.singleton;

public class Singleton5 {
	private static Singleton5 instance;
	private Singleton5() {}
	
	public static Singleton5 getInstance() {
		synchronized (Singleton5.class) {
			if(instance == null) {
				instance = new Singleton5();
			}
		}
		return instance;
	}
}
