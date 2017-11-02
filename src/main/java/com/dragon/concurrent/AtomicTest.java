package com.dragon.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性问题
 * 一、i++ 的原子性问题 ： 
 *  int i= 10；
 *  i = i++;  //此时i的值是10
 *  
 *  i++的操作实际上分为三个步骤“读-改-写” :
 *  		int temp = i;
 *  		i = i + 1;
 *  		i = temp
 *  
 * 二、原子变量 : jdk1.5之后java.util.concurrent.atomic包下提供了大量的原子变量
 *     原子变量有2个特点：
 *     1、变量经过volatile修饰保证了内存可见性
 *     2、通过CAS（Compare-And-Swap）算法保证了数据的原子性
 * 
 *  
 * @author wanglei
 *
 */
public class AtomicTest {
	public static void main(String[] args) {
		AtomicDemo ad = new AtomicDemo();
		for (int i = 0; i < 10; i++) {
			new Thread(ad).start();
		}
	}
}

class AtomicDemo implements Runnable{
	private AtomicInteger serialNumber = new AtomicInteger();
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		
		System.out.println(getSerialNumber());
	}
	public int getSerialNumber() {
		return serialNumber.getAndIncrement(); //i++，先获取在加
//		       serialNumber.incrementAndGet()  //++i，先加后获取
	}
}
