package com.dragon.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile的原子性
 * @author wanglei
 */
public class VolatileDemo2 {
	public static void main(String[] args) {
		MyData1 data = new MyData1();
		
		for ( int i = 0; i < 20; i++) {
			new Thread(() -> {
				for (int j = 1; j <= 1000; j++) {
					data.addPlus();
				}
			}, String.valueOf(i)) .start();
		}
		
		//等待上面的20线程全部都计算完毕，这里之所以要大于2，因为有main线程和gc线程
		while(Thread.activeCount() > 2) {
			Thread.yield();
		}
		
		System.out.println(Thread.currentThread().getName() + " finally number is : " + data.number);
	}

}

class MyData1{
	AtomicInteger number = new AtomicInteger();
	public void addPlus() {
		//number.incrementAndGet()    // = ++i
		number.getAndIncrement();     // = i++
	}
}
