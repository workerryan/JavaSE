package com.dragon.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> { System.out.println("开始召唤神龙"); }) ;
		
		for(int i = 1; i <= 7; i++) {
			final int temp = i;
			new Thread(() -> { 
				System.out.println(Thread.currentThread().getName() + "收集到第 " + temp + " 颗龙珠");
				try{cyclicBarrier.await(); }catch(Exception e) {}  //线程没跑完，需要等待
			}, String.valueOf(i)).start();
		}
	}
}
