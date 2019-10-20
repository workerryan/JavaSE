package com.dragon.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch :闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运行才继续执行
 * CountDownLatch的原理就是先声明一个阈值，然后每次计算的时候阈值减1，当阈值为0位的时候，当前线程才开始运行
 * 示例，计算多个线程的操作时间
 * 在下面的代码中
 * 代码1处：声明了阈值为5的闭锁
 * 代码2处：声明了5个线程，这里的线程数量需要和上面声明的闭锁数量一致，因为每次线程运行的时候，闭锁数量减1
 * 代码3处：(主)线程等待。当阈值为0的时候，该(主)线程继续执行
 * 代码4处：每个线程运行的时候，需要对闭锁阈值减1
 *
 */
public class CountDownLatchTest2 {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(5); //代码1
		 for (int i = 1; i < 6; i++) {                //代码2
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t 被灭");
				latch.countDown();         //代码4
			}, CountryEnum.get(i).getName()).start();
		}
		 
		 latch.await();       //代码3
		 System.out.println(Thread.currentThread().getName() + "\t 秦统一");
	}

	private static void closeDoor() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(5); //代码1
		 for (int i = 0; i < 5; i++) {                //代码2
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t 离开教室");
				latch.countDown();         //代码4
			}, String.valueOf(i)).start();
		}
		 
		 latch.await();       //代码3
		 System.out.println(Thread.currentThread().getName() + "\t 班长最后关灯走人");
	}
}
 
enum CountryEnum {
	ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "韩"), SIX(6, "魏");
	
	private int code;
	private String name;
	
	CountryEnum(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	public static CountryEnum get(int index) {
		CountryEnum[] array = CountryEnum.values();
		for(CountryEnum element : array) {
			if(index == element.getCode()) {
				return element;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}