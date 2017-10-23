package com.dragon.javase8.lambda.methodref;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;

/**
 * 一、方法引用: 若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”(可以理解为方法引用是Lambda表达式的另一种表现形式)
 * 注意： 
 *     1、Lambda体中调用方法的参数和返回值要和函数式接口中抽象方法的函数列表和返回值类型需要一一对应
 *     2、若Lambda参数列表中的第一参数是 实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName :: method
 * 方法引用有三种语法格式:
 * 1、对象::实例方法名  见test1()和test2() 
 * 2、类::静态方法名   见test3()
 * 3、类::实例方法名   见test4()
 *     
 * 二、构造器引用
 *     格式: ClassName :: new    见test5()、test6()
 * 注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致！
 * 
 * 三、数组引用
 *    Type :: new;
 * 
 * @author wanglei
 *
 */
public class LambdaMethodRef {
	
	@Test
	public void test1() {
		Consumer<String> com = x -> System.out.println(x);
		
		PrintStream ps = System.out;
		Consumer<String> com1 = ps :: println;
		
		//需要实现的参数列表类型以及返回值和方法引用的参数列表和返回值是一致的才能这样做
		Consumer<String> com2 = System.out :: println;
		com2.accept("abcd");
	}
	
	@Test
	public void test2() {
		Employee emp = new Employee();
		emp.setName("zhangsan");
		emp.setAge(18);
		Supplier<String> sup = () -> emp.getName();
		String str = sup.get();
		System.out.println(str);
		
		System.out.println("---------");
		Supplier<Integer> sup2 = emp :: getAge;
		Integer age = sup2.get();
		System.out.println(age);
	}

	
	@Test
	public void test3() {
		
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		
		Comparator<Integer> com2 = Integer :: compare;
		
		System.out.println(com2.compare(4, 4));
	}
	
	@Test
	public void test4() {
		BiPredicate<String, String> bp = (x, y) -> x.equals(y);
		
		//要使用这种  类::实例方法名  的形式，需要有个前提，这个前提就是参数的顺序和位置必须一一对应，
		//比如上面的(x, y) -> x.equals(y)，第一个参数x是实例方法(x.equals(y))的调用者，第二个参数是实例方法的参数时才能使用 类::实例方法名
		BiPredicate<String, String> bp2 = String :: equals;
		
		System.out.println(bp2.test("aa", "bbb"));
	}
	
	@Test
	public void test5() {
		Supplier<Employee> sup = () -> new Employee();
		Employee employee = sup.get();
		
		//构造器引用方法
		Supplier<Employee> sup2 = Employee :: new;
	}
	
	@Test
	public void test6() {
		//构造器引用方法
		Function<Integer, Employee> fun = x -> new Employee(x);
		Employee emp = fun.apply(19);
		System.out.println(emp);
	}
	
	@Test
	public void test7() {
		Function<Integer, String[]> fun = x -> new String[x];
		String[] strs = fun.apply(10);
		System.out.println(strs.length);
		
		Function<Integer, String[]> fun2 = String[] :: new;
		String[] strs2 = fun2.apply(20);
		System.out.println(strs2.length);
	}
}
