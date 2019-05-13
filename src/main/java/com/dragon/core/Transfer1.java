package com.dragon.core;

public class Transfer1 {
	static int s;
	int i;           //代码1
	int j;           //代码2
	{
		int i = 1;   //代码3
		i++;         //代码4
		j++;         //代码5
		s++;         //代码6
	}
	
	public void test(int j) { //代码7
		j++;         //代码8
		i++;         //代码9
		s++;         //代码10
	}
	
	
	public static void main(String[] args) {  //代码11
		Transfer1 trans1 = new Transfer1();   //代码12
		Transfer1 trans2 = new Transfer1();   //代码13
		
		trans1.test(10);   //代码14
		trans1.test(20);   //代码15
		trans2.test(30);   //代码16
		
		System.out.println(trans1.i + "," + trans1.j + "," + trans1.s);
		System.out.println(trans2.i + "," + trans2.j + "," + trans2.s);
	}
}
