package com.dragon.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
 * 
 * 在ThreadPoolTest中，写了关于线程池的相关代码，该类主要是了解线程池的调度
 * @author wanglei
 *
 */
public class ThreadPoolScheduleTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1、创建5个线程的固定线程池
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		
		//启动10个线程
		for (int i = 0; i < 10; i++) {
			/**
			 *  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
			 *  第一个参数是实现Runnable或者Callback的线程，
			 *  第二个参数是延迟时间
			 *  第三个参数是延迟时间的单位
			 */
			ScheduledFuture<Integer> result = pool.schedule(() -> {
				int num = new Random().nextInt(100);
				System.out.println(Thread.currentThread().getName() + ":" + num);
				return num;
			}, 3, TimeUnit.SECONDS);
			
			System.out.println(result.get());
		}
		
		pool.shutdown();
	}
}