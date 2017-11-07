package com.dragon.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁需要显式声明和释放锁
 * @author wanglei
 *
 */
public class LockTest {
	public static void main(String[] args) {
		Ticket tk = new Ticket();
		
		new Thread(tk, "1号窗口").start();
		new Thread(tk, "2号窗口").start();
		new Thread(tk, "3号窗口").start();
	}
}

class Ticket implements Runnable{
	private int ticket = 100;
	private Lock lock = new ReentrantLock();
	@Override
	public void run() {
		while(true) {
//			lock.lock(); //1、手动上锁
			
			try {
				if(ticket > 0) {
					Thread.sleep(2);
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为: " + --ticket);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
//				lock.unlock(); //2、手动释放上锁
			}
		}
	}
}