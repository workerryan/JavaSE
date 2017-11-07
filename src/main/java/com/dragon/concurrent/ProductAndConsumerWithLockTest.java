package com.dragon.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用同步锁解决线程的唤醒和等待
 * @author wanglei
 *
 */
public class ProductAndConsumerWithLockTest {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		Productor productor = new Productor(clerk);
		Consumer consumer = new Consumer(clerk);
		
		new Thread(productor, "生产者A").start();
		new Thread(consumer, "消费者B").start();
		
		new Thread(productor, "生产者C").start();
		new Thread(consumer, "消费者D").start();
	}
}

//店员
class Clerk2{
	private int product = 0;
	private Lock lock = new ReentrantLock();   //1   
	private Condition condition = lock.newCondition(); //2    
	
	//进货
	public void get() {
		lock.lock();
		try {
			while(product >= 1) {
				System.out.println("产品已满，无法添加");
				
				try {
					condition.await();  //3  
				} catch (InterruptedException e) {
				}
			}
			System.out.println(Thread.currentThread().getName() + ":" + ++product);
			condition.signalAll();  //4    
		}finally {
			lock.unlock();
		}
		
	}
	//卖货
	public void sale() {
		lock.lock();
		try {
			while(product <= 0) {
				System.out.println("缺货");
				try {
					condition.await();
				} catch (InterruptedException e) {
				}
				
				System.out.println(Thread.currentThread().getName() + ":" + --product);
				condition.signalAll();
			}
		}
		finally {
			lock.unlock();
		}
	}
}

//生产者
class Productor2 implements Runnable{
	private Clerk2 clerk;
	private Lock lock = new ReentrantLock();
	
	public Productor2(Clerk2 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		lock.lock();
		for(int i = 0 ; i < 30; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			clerk.get();
		}
	}
}

//消费者
class Consumer2 implements Runnable{
	private Clerk clerk;

	public Consumer2(Clerk clerk) {
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		for(int i = 0 ; i < 30; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			clerk.sale();
		}
	}
}