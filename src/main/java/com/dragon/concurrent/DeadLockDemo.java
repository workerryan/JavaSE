package com.dragon.concurrent;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {
	private String lockA;
	private String lockB;
	public HoldLockThread(String lockA, String lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}
	@Override
	public void run() {
		synchronized(lockA) {
			System.out.println(Thread.currentThread().getName() + "\t持有" + lockA + "\t尝试获取" + lockB);
			try { TimeUnit.SECONDS.sleep(2); }catch(Exception e) {}
			synchronized(lockB) {
				System.out.println(Thread.currentThread().getName() + "\t持有" + lockB + "\t尝试获取" + lockA);
			}
		}
	}
}

public class DeadLockDemo {
	public static void main(String[] args) {
		String lockA = "lockA";
		String lockB = "lockB";
		
		new Thread(new HoldLockThread(lockA, lockB), "AA").start();
		new Thread(new HoldLockThread(lockB, lockA), "BB").start();
	}
}
