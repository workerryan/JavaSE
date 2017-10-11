package com.dragon.javase8.lambda.demo;

/**
 * 根据年龄过滤
 * @author wanglei
 *
 */
public class EmployeeFilterByAge implements IEmployeeFilter<Employee> {

	@Override
	public boolean filterEmployee(Employee t) {
		return t.getAge() >= 35;
	}

}
