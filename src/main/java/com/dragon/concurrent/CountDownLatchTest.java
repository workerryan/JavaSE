package com.dragon.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch :闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运行才继续执行
 * CountDownLatch的原理就是先声明一个阈值，然后每次计算的时候阈值减1，当阈值为0位的时候，当前线程才开始运行
 * 示例，计算多个线程的操作时间
 * 在下面的代码中
 * 代码1处：声明了阈值为10的闭锁
 * 代码2处：声明了10个线程，这里的线程数量需要和上面声明的闭锁数量一致，因为每次线程运行的时候，闭锁数量减1
 * 代码3处：(主)线程等待。当阈值为0的时候，该(主)线程继续执行
 * 代码4处：每个线程运行的时候，需要对闭锁阈值减1
 *
 */
public class CountDownLatchTest {
	
	
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(10);	//1 
		LatchDemo ld = new LatchDemo(latch);
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < 10; i++) {		//2
			new Thread(ld).start();
		}
		
		try {
			latch.await(); //等待 3
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		long end = System.currentTimeMillis();
		
		System.out.println("总消耗时间为: " + (end - start));
	}
}

class LatchDemo implements Runnable{
	private CountDownLatch latch;

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized(this) {
			try {
				for (int i = 0; i < 500000; i++) {
					if(i % 2 == 0) {
						System.out.println(i);
					}
				}
			}finally{
				latch.countDown(); //减少一个阈值  4
			}
		}
	}
}
