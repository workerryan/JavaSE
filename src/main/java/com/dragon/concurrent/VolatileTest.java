package com.dragon.concurrent;

/**
 * 一、volatile关键字 : 当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 *     volatile相较于synchronized是一种较为轻量级的同步策略。
 * @author wanglei
 *
 */
public class VolatileTest {
	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();
		
		while(true) {
			if(td.isFlag()) {
				System.out.println("-- 进入main线程");
				break;
			}
		}
	}
}

class ThreadDemo implements Runnable{
	private volatile boolean flag = false;
	
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		flag = true;
		
		System.out.println("flag = " + isFlag());
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}