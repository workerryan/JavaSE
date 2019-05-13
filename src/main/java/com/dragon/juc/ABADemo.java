package com.dragon.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
	//接收2个参数，第一个是初始化的引用值，第二个就是时间版本号了
	static AtomicStampedReference<Integer> value = new AtomicStampedReference<Integer>(100, 1);
	
	public static void main(String[] args) {
		new Thread(() ->  {
			int stamp = value.getStamp(); //获取版本号
			System.out.println("t1 的第1次版本号: " + stamp);
			//暂停1秒，为了下面的t2和自己拿到一样的版本号
			try {
				TimeUnit.SECONDS.sleep(1);
			}catch(InterruptedException e) { }
			//t1做一次ABA
			value.compareAndSet(100, 101, value.getStamp(), value.getStamp() + 1);
			System.out.println("t1 的第2次版本号: " + value.getStamp());
			
			value.compareAndSet(101, 100, value.getStamp(), value.getStamp() + 1);
			System.out.println("t1 的第3次版本号: " + value.getStamp());
		}, "t1") .start();
		
		new Thread(() ->  {
			int stamp = value.getStamp(); //获取版本号
			System.out.println("t2 的版本号: " + stamp);
			
			//暂停4秒，为了保证上面的t1线程完成一次ABA操作
			try {
				TimeUnit.SECONDS.sleep(3);
			}catch(InterruptedException e) {  }
			
			boolean result = value.compareAndSet(100, 1024, stamp, stamp + 1);
			System.out.println("t2 修改结果: " + result + " 当前的版本号是: " + value.getStamp() + 
					" 当前的值为" + value.getReference());
		}, "t2") .start();
	}
}
