package com.dragon.javase8.lambda.demo;

/**
 * 过于Employee的一些信息
 * @author wanglei
 *
 */
public interface IEmployeeFilter<T> {
	public boolean filterEmployee(T t);
}
