package com.dragon.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池
 * 一、线程池的体系结构：
 *     java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 *         |- ExecutorService 子接口，线程池的主要接口
 *             |- ThreadPoolExecutor 实现类
 *             |- ScheduledExecutorService 子接口, 负责线程的调度
 *                 |- ScheduledThreadPoolExecutor 实现类，继承了ThreadPoolExecutor并实现了ScheduledExecutorService，所以具备线程池和线程调度
 * 二、创建线程池的工具类 Executors
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建当线程次。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 * 
 * 要使用线程池，基本的步骤就是:1、创建线程池；2、分配任务；3、关闭线程池
 * @author wanglei
 *
 */
public class ThreadPoolTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1、创建5个线程的固定线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		
		ThreadPoolDemo tpd = new ThreadPoolDemo();
		
		//2、为线程池中的线程分配任务
		for (int i = 0; i < 5; i++) {
			pool.submit(tpd);
		}
		
		//3、关闭线程池
		pool.shutdown();  //安全关闭，等待线程池的所有任务都执行完毕了，在关线程池，不会再接收任务
//		pool.shutdownNow() //立马关闭
		
		System.out.println("-------------------");
		
		//接收Callbale
		ExecutorService pool2 = Executors.newFixedThreadPool(5);
		List<Future<Integer>> list = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Future<Integer> future = pool2.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int j = 0; j < 100; j++) {
						sum += j;
					}
					return sum;
				}
			});
			list.add(future);
		}
		
		pool2.shutdown();
		
		for (Future<Integer> future : list) {
			System.out.println(future.get());
		}
	}
}

class ThreadPoolDemo implements Runnable{
	private int i = 0;
	
	@Override
	public void run() {
		while ( i <= 100) {
			System.out.println(Thread.currentThread().getName() + " : " + ++i);
		}
	}
}