package com.dragon.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这三个线程的ID分别为 A、B、C，每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示
 * 如ABCABCABCABC...
 */
public class ABCAlternate {
	public static void main(String[] args) {
		AlternateDemo ad = new AlternateDemo();
		new Thread(() -> {
			for (int i = 0; i <= 20; i++) {
				ad.loopA(i);
			}
		}, "A").start();
		
		new Thread(() -> {
			for (int i = 0; i <= 20; i++) {
				ad.loopB(i);
			}
		}, "B").start();
		
		new Thread(() -> {
			for (int i = 0; i <= 20; i++) {
				ad.loopC(i);
			}
		}, "C").start();
	}
}


class AlternateDemo {
	private int number = 1; //当前正在执行线程的标记
			
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();
	
	/**
	 * @param totalLoop 第几轮循环
	 */
	public  void loopA(int totalLoop) {
		lock.lock();
		
		try {
			//1、判断
			while(number != 1) {
				try {
					condition1.await();
				} catch (InterruptedException e) {
				}
			}
			//2、打印
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			//3、唤醒
			number = 2 ;
			condition2.signal();  //这时候只能唤醒2，而不能唤醒所有
		}finally {
			lock.unlock();
		}
	}
	
	public  void loopB(int totalLoop) {
		lock.lock();
		
		try {
			//1、判断
			while(number != 2) {
				try {
					condition2.await();
				} catch (InterruptedException e) {
				}
			}
			//2、打印
			for (int i = 1; i <= 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			//3、唤醒
			number = 3 ;
			condition3.signal();   
		}finally {
			lock.unlock();
		}
	}
	
	public  void loopC(int totalLoop) {
		lock.lock();
		
		try {
			//1、判断
			while(number != 3) {
				try {
					condition3.await();
				} catch (InterruptedException e) {
				}
			}
			//2、打印
			for (int i = 1; i <= 20; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			//3、唤醒
			number = 1 ;
			condition1.signal();   
		}finally {
			lock.unlock();
		}
	}
}
