package com.dragon.se.singleton;

public class Singleton6 {
	private static Singleton6 instance;
	private Singleton6() {}
	
	public static Singleton6 getInstance() {
		if(instance == null) {
			synchronized (Singleton6.class) {
				if(instance == null) {
					instance = new Singleton6();
				}
			}
		}
		return instance;
	}
}
