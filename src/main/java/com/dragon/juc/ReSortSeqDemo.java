package com.dragon.juc;

public class ReSortSeqDemo {
	int a = 0;
	boolean flag = false;

	public void method1() {
		a = 1; // 语句1
		flag = true; // 语句2
	}

	public void method2() {
		if (flag) {
			a = a + 5;
			System.out.println("a = " + a);
		}
	}
}
