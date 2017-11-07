package com.dragon.concurrent;

import com.dragon.concurrent.number.Number1;

/**
 * 题目  : 判断打印结果
 * 1、两个普通同步方法，两个线程标准打印，打印的结果是    // one two
 * 2、在getOne()方法里面Thread.sleep(),打印的结果是   // one two
 * 3、新增一个普通方法getThree，打印的结果是           // Three one two
 * 4、两个普通同步方法，2个number对象，number1调用getOne, number2调用getTwo, 打印结果是    //two  one
 * 5、修改getOne()方法为静态同步方法，一个number对象， 打印结果是  //two  one
 * 6、2个都是静态同步方法，一个number对象，打印结果是   // one two
 * 7、1个普通同步方法，1个静态同步方法，2个number对象，number1调用getOne, number2调用getTwo, 打印结果是    //two  one
 * 8、两个静态同步方法，2个number对象，打印结果是      // one two
 * 
 * 线程8锁的关键点：
 * a、非静态方法的锁默认为this，静态方法的锁为对应的Class实例（本例为Number1.class）
 * b、在某一时刻内，只能有一个线程持有锁，无论有几个方法。
 * 
 * @author wanglei
 *
 */
public class Thread8MonitorTest {
	public static void main(String[] args) {
		Number1 number1 = new Number1();
		Number1 number2 = new Number1();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				number1.getOne();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				number2.getTwo();
			}
		}).start();
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				number1.getThree();
//			}
//		}).start();
	}
}


