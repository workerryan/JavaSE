package com.dragon.concurrent.cp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1，一个减1，进行5轮操作。
 * 这里使用阻塞队列
 * @author wanglei
 *
 */
public class ConsumerProductDemo2 {
	public static void main(String[] args) throws Exception{
		ShareData2 data = new ShareData2(new ArrayBlockingQueue<>(10));
		
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+" 开始生产");
			try {
				data.prod();
			} catch (Exception e) {
			}
		}, "a").start();
		
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+" 开始消费");
			try {
				data.consumer();
			} catch (Exception e) {
			}
		}, "b").start();
		
		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName()+" 停止生产消费");
		data.stop();
	}
}

class ShareData2 {
	private volatile boolean FLAG = true; //默认开启，进行生产+消费
	private AtomicInteger atomicInteger = new AtomicInteger();
	BlockingQueue<String> blockingQueue = null;
	public ShareData2(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	public void prod() throws Exception {
		String data = null;
		boolean retValue;
		while(FLAG) {
			data = atomicInteger.incrementAndGet() + "";
			retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
			if(retValue) {
				System.out.println(Thread.currentThread().getName()+" 插入数据" + data + "成功");
			}else {
				System.out.println(Thread.currentThread().getName()+" 插入数据" + data + "失败");
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName()+" flag为false，停止生产");
	}
	
	public void consumer() throws Exception {
		String data = null;
		while(FLAG) {
			data = blockingQueue.poll(2L, TimeUnit.SECONDS);
			if(null == data || data.equalsIgnoreCase("")) {
				FLAG = false;
				System.out.println(Thread.currentThread().getName()+" 超过2秒没有取到数据，消费退出");
				return;
			}
			System.out.println(Thread.currentThread().getName()+" 消费成功，data=" + data);
		}
	}
	
	public void stop() {
		this.FLAG = false;
	}
	
}