package com.dragon.javase8.lambda.demo2;

/**
 * 计算的接口
 * @author wanglei
 *
 */
@FunctionalInterface
public interface CalcFunction<T, R> {

	/**
	 * 将t1和t2做运算并且将结果返回
	 * @param t1
	 * @param t2
	 * @return
	 */
	public R getValue(T t1, T t2);
}
