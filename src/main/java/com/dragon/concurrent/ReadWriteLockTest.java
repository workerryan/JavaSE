package com.dragon.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1、ReadWeiteLock ： 读写锁
 *     当满足下面2个条件的时候：
 *     1、写写 / 读写 这2中情况需要互斥
 *     2、读读是不需要互斥的
 *     就可以使用读写锁
 */
public class ReadWriteLockTest {
	public static void main(String[] args) {
		ReadWriteLockDemo rw = new ReadWriteLockDemo();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				rw.set( (int)(Math.random() * 100));
			}
		}, "Write").start();
		
		for(int i = 0; i< 100 ; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					rw.get();
				}
			}, "Reader").start();
		}
	}

}

class ReadWriteLockDemo{
	private int number = 0;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	//读
	public void get() {
		lock.readLock().lock(); //上锁
		try{
			System.out.println(Thread.currentThread().getName() + ":" + number);
		}finally {
			lock.readLock().unlock();  //释放锁
		}
	}
	//写
	public void set(int number) {
		lock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName() );
			this.number = number;
		}finally {
			lock.writeLock().unlock();
		}
	}
}