package com.dragon.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLockDemo {
	AtomicReference<Thread> atomicReference = new AtomicReference<>();
	
	//尝试获得锁，只有当引用对象里面是null的时候，才获得，并且将当前线程放进去
	public void myLock() {
		Thread thread = Thread.currentThread();
		System.out.println(thread.getName() + "\t come in");
		//while循环当有线程持有atomicReference的时候，就会不停的自旋
		while(!atomicReference.compareAndSet(null, thread)) {
			
		}
	}
	//解锁
	public void myUnlock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(thread.getName() + "\t invoke unlock");
	}
	
	public static void main(String[] args) {
		SpinLockDemo demo = new SpinLockDemo();
		
		new Thread(()->{
			demo.myLock();
			try{TimeUnit.SECONDS.sleep(5); }catch(Exception e) {}
			demo.myUnlock();
		}, "aa").start();
		
		//这里的睡眠是为了保证aa线程一定启动了
		try{TimeUnit.SECONDS.sleep(1); }catch(Exception e) {}
		
		new Thread(()->{
			demo.myLock();
			demo.myUnlock();
		}, "bb").start();
	}
}
