package com.dragon.se;

public class Incr {
	public static void main(String[] args) {
		int i = 1;	
		i = i++;                  //2
		int j = i++;
		int k = i + ++i * i ++;
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
	}
}
