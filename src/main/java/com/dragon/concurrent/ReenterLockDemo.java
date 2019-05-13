package com.dragon.concurrent;

import java.util.concurrent.locks.ReentrantLock;
 
public class ReenterLockDemo {
	public static void main(String[] args) {
		Phone phone = new Phone();
		
		new Thread(phone, "t1").start();
		new Thread(phone, "t2").start();
	}
}

class Phone implements Runnable {
	ReentrantLock lock = new ReentrantLock();
	
	@Override
	public void run() {
		get() ;
	}

	public void get() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke get()");
			set();
		}finally {
			lock.unlock(); //代码1
		}
	}
	public void set() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke set()");
		}finally {
			lock.unlock();
		}
	}
}