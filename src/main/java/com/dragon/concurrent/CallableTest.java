package com.dragon.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程的方式三：实现Callable接口。相较于实现Runnable接口的方式，方法可以有返回值，并且可以抛出异常
 * 执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果。FutureTask是Future接口的实现类
 * @author wanglei
 *
 */
public class CallableTest {
	public static void main(String[] args) {
		ThreadDemo2 td = new ThreadDemo2();
		
		FutureTask<Integer> result = new FutureTask<>(td);
		new Thread(result).start();
		
		//接收线程运算的结果
		try {
			//当上面线程没有执行完毕的时候，是不会执行result.get()方法的，
			//也就是说FutureTask也可以用作闭锁
			System.out.println(result.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo2 implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			sum += i;
		}
		return sum;
	}
}