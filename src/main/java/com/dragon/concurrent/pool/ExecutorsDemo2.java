package com.dragon.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsDemo2 {
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		ExecutorService threadPool = 
				new ThreadPoolExecutor(2, 5, 1L, TimeUnit.SECONDS, 
						new LinkedBlockingQueue<>(3), 
						Executors.defaultThreadFactory(), 
						//这里使用调用者运行拒绝策略
						new ThreadPoolExecutor.DiscardPolicy());
		
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
