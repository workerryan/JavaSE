package com.dragon.javase8.lambda.demo2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;

public class LambdaTest {
	
	List<Employee> employees = Arrays.asList(
			new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 5559.55),
			new Employee("王五", 50, 6666.66),
			new Employee("赵六", 16, 2222.22),
			new Employee("田七", 17, 7777.77),
			new Employee("陈八", 35, 1111.11),
			new Employee("沈九", 19, 8888.88)
			);

	/**
	 * 先按年龄再按名称排序
	 */
	@Test
	public void test1() {
		Collections.sort(employees, (e1, e2) -> {
			if(e1.getAge() == e2.getAge()) {
				return e1.getName().compareTo(e2.getName());
			}else {
				return Integer.compare(e1.getAge(), e2.getAge());
			}
		});
		
		for(Employee e : employees) {
			System.out.println(e);
			System.out.println(e.getName().toUpperCase());
		}
	}
	
	@Test
	public void test2() {
		String str = strHandler("abcdefghijklmn", x -> x.toUpperCase());
		System.out.println(str);
		
		String str2 = strHandler("abcdefghijklmn", x -> {
			String result = x.toUpperCase();
			return result.substring(2, 5);
		});
		System.out.println(str2);
	}
	
	/**
	 * 使用Lambda的好处就在于实现内容可以随意写。
	 * 如果是按照传统方法，那么要声明一个接口，再需要声明2个实现类，而现在实现类只需要一个方法就搞定了。
	 */
	@Test
	public void test3() {
		System.out.println(calcAdd(100L, 200L, (x ,y) -> x + y));
		
		System.out.println(calcAdd(100L, 200L, (x ,y) -> x * y));
	}

	/**
	 * 这个方法是必须的，因为从本质上说，Lambda是一个匿名函数，即使用传统方法，也需要些一个实现类。
	 * 所有这个方法是必须存在的
	 * @param str  待处理的字符串参数
	 * @param fu   接口
	 * @return
	 */
	private String strHandler(String str, CaseUpperFunction fu) {
		return fu.caseUpper(str);
	}
	
	/**
	 * 对a和b进行CalcFunction处理
	 * @param a
	 * @param b
	 * @param cf
	 * @return
	 */
	private Long calcAdd(Long a, Long b, CalcFunction<Long, Long> cf) {
		return cf.getValue(a, b);
	}
	
}
