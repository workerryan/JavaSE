package com.dragon.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
	public static void main(String[] args) {
		//模拟3个车位，默认是非公平锁
		Semaphore semaphore = new Semaphore(3);
		
		for(int i = 1; i <= 6; i++) {
			new Thread(() -> { 
				try{
					semaphore.acquire(); //抢位置
					System.out.println(Thread.currentThread().getName() + "抢到车位");
					TimeUnit.SECONDS.sleep(3);
					System.out.println(Thread.currentThread().getName() + "停车3秒后，离开车位");
				}catch(Exception e) {
					
				}finally {
					semaphore.release(); //开车走了，释放车位
				}
			}, String.valueOf(i)).start();
		}
	}
}
