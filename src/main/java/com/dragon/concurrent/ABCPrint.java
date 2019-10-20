package com.dragon.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class ShareData {
	private int number = 1; //
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();
	
	public void printA() {
		try {
			lock.lock();
			if ( number != 1) {
				condition1.await();
			}
			
			System.out.println(Thread.currentThread().getName() + " print A");
			number = 2;
			condition2.signal();
			
		}catch(Exception e) {
		}finally {
			lock.unlock();
		}
	}
	
}