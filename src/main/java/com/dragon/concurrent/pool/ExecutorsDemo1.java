package com.dragon.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo1 {
	public static void main(String[] args) {
		//声明固定5个线程的线程池
		//ExecutorService threadPool = Executors.newFixedThreadPool(5);       //代码1
		//ExecutorService threadPool = Executors.newSingleThreadExecutor(); //代码2
		ExecutorService threadPool = Executors.newCachedThreadPool();     //代码3
		
		try {
			//模拟10个业务，每个业务是外部的请求
			for(int i=1; i <= 10; i++) {
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "\t办理业务");
				});
			}
		}finally {
			threadPool.shutdown();
		}
		
	}
}
