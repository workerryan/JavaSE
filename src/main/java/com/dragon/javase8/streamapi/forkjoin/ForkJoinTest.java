package com.dragon.javase8.streamapi.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class ForkJoinTest {

	/**
	 * 使用Fork-Join计算，Fork-Join需要ForkJoinPool的支持
	 */
	@Test
	public void test1() {
		Instant start = Instant.now();
		
		ForkJoinPool pool = new ForkJoinPool();
		RecursiveTask<Long> task= new ForkJoinCalculate(0, 100000000000L);
		Long sum = pool.invoke(task); //执行汇总
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("耗时:" + Duration.between(start, end).toMillis() +"毫秒"); //24864毫秒
	}
	
	/**
	 * 使用普通的for循环累加
	 */
	@Test
	public void test2() {
		Instant start = Instant.now();
		long sum = 0;
		for(long i = 0; i <=100000000000L; i++ ) {
			sum += i;
		}
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("耗时:" + Duration.between(start, end).toMillis() +"毫秒"); //35196毫秒
	}
	
	//分别执行上面2个方法，当数据比较大的时候，Fork-Join胜，数据量小得时候，for胜，因为Fork-Join的拆分也是需要时间的
	
	/**
	 * Java8的并行流
	 * Stream API 可以声明性地通过 parallel() 与 sequential() 在并行流与顺序流之间进行切换。
	 */
	@Test
	public void test3() {
		Instant start = Instant.now();
		
		long sum = LongStream.rangeClosed(0, 100000000000L)
			.parallel()  //将流切换为并行流，其底层实现其实还是Fork-Join
			.reduce(0 , Long :: sum);  
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("耗时:" + Duration.between(start, end).toMillis() +"毫秒"); //13654
	}
}
