package com.dragon.concurrent.cp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1，一个减1，进行5轮操作。
 * @author wanglei
 *
 */
public class ConsumerProductDemo1 {
	public static void main(String[] args) {
		ShareData data = new ShareData();
		
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				data.increment();
			}
		}, "a").start();
		
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				data.decrement();
			}
		}, "b").start();
	}
}

class ShareData {
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void increment(){
		try {
			lock.lock();
			while (number != 0) {
				//有数量，需要等待
				condition.await();
			}
			//生成
			number ++;
			System.out.println(Thread.currentThread().getName() + "\t " + number);
			//通知唤醒消费者
			condition.signalAll();
		}catch(Exception e) {
			
		}
		finally {
			lock.unlock();
		}
	}
	
	public void decrement() {
		try {
			lock.lock();
			while (number == 0) {
				//有数量，需要等待
				condition.await();
			}
			//生成
			number --;
			System.out.println(Thread.currentThread().getName() + "\t " + number);
			//通知唤醒消费者
			condition.signalAll();
		}catch(Exception e) {
			
		}finally {
			lock.unlock();
		}
	}
}