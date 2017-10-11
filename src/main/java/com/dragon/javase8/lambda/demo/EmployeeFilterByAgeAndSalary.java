package com.dragon.javase8.lambda.demo;


/**
 * 按照年级和薪水进行过滤
 * @author wanglei
 *
 */
public class EmployeeFilterByAgeAndSalary implements IEmployeeFilter<Employee>{

	@Override
	public boolean filterEmployee(Employee emp) {
		return emp.getAge() >= 35 && emp.getSalary() > 5000;
	}

}
