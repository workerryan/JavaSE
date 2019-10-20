package com.dragon.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableDemo {
	public static void main(String[] args) throws Exception{
		FutureTask<Integer> task = new FutureTask<>(new MyThread());
		
		new Thread(task, "aa").start(); //代码1
		new Thread(task, "bb").start(); //代码2
		
		//模拟主线程的运算
		int result1 = 100;
		
		//获取callable线程的计算结果，如果没有计算完成会阻塞，直到计算完成
		//获取Future的计算结果要放到最后
		int result2 = task.get();
		System.out.println("result = " + (result1 + result2));
	}
}

class MyThread implements Callable<Integer>{
	@Override
	public Integer call() throws Exception{
		System.out.println(Thread.currentThread().getName() + " call in");
		return 1024;
	}
}