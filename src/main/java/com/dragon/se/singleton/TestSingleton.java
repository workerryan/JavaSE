package com.dragon.se.singleton;

public class TestSingleton {
	public static void main(String[] args) {
		Singleton2 s = Singleton2.INSTANCE;
		System.out.println(s);
	}
}
