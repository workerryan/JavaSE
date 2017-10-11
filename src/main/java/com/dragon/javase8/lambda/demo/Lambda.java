package com.dragon.javase8.lambda.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;
import com.dragon.javase8.lambda.demo.EmployeeFilterByAge;
import com.dragon.javase8.lambda.demo.EmployeeFilterByAgeAndSalary;
import com.dragon.javase8.lambda.demo.IEmployeeFilter;

/**
 * Java8的Lambda表达式 (匿名内部类)
 * 1、在传统方法中，要过一些数据进行过滤，比如要对List集合里面的数据根据某些条件进行，那么就需要在代码中写上for循环，在循环体内根据条件进行过滤，比如下面的test3()。
 *     这种方法如果要增加过滤条件，那么就需要写多个方法，在每个方法的for循环内进行对应的条件过滤
 * 2、传统方法的优化方案，引入设计模式，设计一个接口(com.dragon.javase8.lambda.IEmployeeFilter<Employee>)，然后每个过滤条件是一个实现了该接口的类，比如下面test4()和test5()。
 *     在上面的传统方法中，每增加一个过滤条件，就需要书写一个方法；而在这种模式中，只需要一个方法，因为过滤的细节都封装到接口实现类里面去了。
 *     这种设计模式叫策略设计模式
 * 3、除了引入上面的设计模式，我们还可以使用匿名内部类来进行排序，如下面的test6()
 * 4、上面3种其实有用的代码就是判断语句，但是我们却写了很多的代码。使用Lambda表达式就可以很大的简洁其效率
 * 5、结合Lambda表达式和Stream。如下test8()
 * 
 * 
 * @author wanglei
 *
 */
public class Lambda {
	List<Employee> employees = Arrays.asList(
			new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 5559.55),
			new Employee("王五", 50, 6666.66),
			new Employee("赵六", 16, 2222.22),
			new Employee("田七", 17, 7777.77),
			new Employee("陈八", 35, 1111.11),
			new Employee("沈九", 19, 8888.88)
			);
	
	@Test
	public void test8() {
		employees.stream()
			.filter((emp) -> emp.getAge() >= 35 && emp.getSalary() > 5000)
			.forEach(System.out::println);
		
		System.out.println("------------");
		employees.stream()
			.filter((emp) -> emp.getAge() >= 35 && emp.getSalary() > 5000)
			.limit(1)  //只取第一个
			.forEach(System.out::println);
		
		System.out.println("------------");
		employees.stream()
			.map(Employee :: getName)
			.forEach(System.out::println);
	}
	
	
	
	/**
	 * 使用Lambda表达式进行过滤
	 */
	@Test
	public void test7() {
		List<Employee> emps = filterEmployee(employees, (emp) -> emp.getAge() >= 35 && emp.getSalary() > 5000);
		emps.forEach(System.out::println);
	}
	
	/**
	 * 使用内部类进行过滤
	 */
	@Test
	public void test6() {
		List<Employee> emps = filterEmployee(employees, new IEmployeeFilter<Employee>() {
			@Override
			public boolean filterEmployee(Employee emp) {
				return emp.getAge() >= 35 && emp.getSalary() > 5000;
			}
		});
		
		for(Employee emp : emps) {
			System.out.println(emp.toString());
		}
	}
	
	
	
	
	
	/**
	 * 获取年级大于35的员工
	 */
	@Test
	public void test5() {
		List<Employee> emps = filterEmployee(employees, new EmployeeFilterByAge());
		
		for(Employee emp : emps) {
			System.out.println(emp.toString());
		}
	}
	
	/**
	 * 获取年级大于35并且工资大于5000的员工
	 */
	@Test
	public void test4() {
		List<Employee> emps = filterEmployee(employees, new EmployeeFilterByAgeAndSalary());
		
		for(Employee emp : emps) {
			System.out.println(emp.toString());
		}
	}
	
	public List<Employee> filterEmployee(List<Employee> list, IEmployeeFilter<Employee> mp){
		List<Employee> emps = new ArrayList<Employee>();
		for(Employee emp : list) {
			if(mp.filterEmployee(emp)) {
				emps.add(emp);
			}
		}
		return emps;
	}
	
	
	
	/**
	 * 传统的过滤方法
	 * 在代码里面通过for循环来进行判断
	 */
	@Test
	public void test3() {
		List<Employee> list = filterEmplyees(employees);
		for(Employee emp : list) {
			System.out.println(emp.toString());
		}
	}
	
	//获取所有年级大于35的员工
	private List<Employee> filterEmplyees(List<Employee> list){
		List<Employee> emps = new ArrayList<Employee>();
		for(Employee emp : list) {
			if(emp.getAge() >= 35) {
				emps.add(emp);
			}
		}
		return emps;
	}
	
	
	@Test
	public void test2() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer> ts = new TreeSet<Integer>(com);
	}
	
	/**
	 * 之前的匿名内部类
	 */
    @Test
    public void test1() {
    		Comparator<Integer> com = new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return Integer.compare(o1, o2);
				}
			};
		TreeSet<Integer> ts = new TreeSet<Integer>(com);
    }
}
