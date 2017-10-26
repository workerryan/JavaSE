package com.dragon.javase8.streamapi.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 使用Fork-Join模式实现并行计算，
 * Fork/Join 框架:就是在必要的情况下，将一个大任务，进行拆分(fork)成若干个 小任务(拆到不可再拆时)，再将一个个的小任务运算的结果进行 join 汇总.
 * 要使用Fork-Join需要继承RecursiveTask<T> 或者RecursiveAction。
 * 这2个类的区别就是RecursiveTask的抽象类有返回值，而RecursiveAction的抽象方法没有返回值
 * 
 * 这个类的场景为计算1到100亿的累加和
 * @author wanglei
 *
 */
public class ForkJoinCalculate extends RecursiveTask<Long>{
	private static final long serialVersionUID = -8721911009457956109L;

	private long start;	//计算起始值
	private long end;   //计算结束值
	private static final long THRESHOLD = 10000; //拆分的临界值
	
	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}


	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THRESHOLD) { //达到临时值，那么就不拆分了，进行累加
			long sum = 0;
			for(long i = start ; i <= end; i++) {
				sum += i;
			}
			return sum;
		}else {  //否则继续拆分
			long middle = (start + end) / 2;
			ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
			left.fork(); //拆分子任务同时压入线程队列
			
			ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
			right.fork();
			
			return left.join() + right.join();
		}
	}




}
