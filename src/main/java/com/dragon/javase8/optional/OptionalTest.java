package com.dragon.javase8.optional;

import java.util.Optional;

import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;
import com.dragon.javase8.optional.model.Godness;
import com.dragon.javase8.optional.model.Man;
import com.dragon.javase8.optional.model.NewMan;

/**
 * Java8提供了一些常用的方法 ：
 * Optional.of(T t) : 创建一个Optional实例  见test1()
 * Optional.empty() : 创建一个空的Optional实例
 * Optional.ofNullable(T t) : 若t不为null，创建Optional实例，否则创建空实例  见test2()、test3()
 *     上面的3个方法中，of方法是不能传递null的，empty()只会构建一个空的实例，ofNullable也可以构建空的实例，也可构建非空实例
 * Optional.isPresent() : 判断是否包含值 见test3()
 * Optional.orElse(T t) : 若调用对象包含值，返回该值，否则返回t 见test3()
 * Optional.orElseGet(Supplier s) : 若调用对象包含值，返回该值，否则返回s获取的值  见test4()
 *     orElse(T t)和orElseGet(Supplier s)的区别就是后者是一个函数接口，可以写复制的判断代码
 * Optional.map(Function f) : 若有值对其处理，并返回处理后的Optional，否则返回Optional.empty()  见test5()
 * Optional.flatMap(Faction mapper) : 与map类似，要求返回值必须是Optional  见test6()
 * 
 * @author wanglei
 * 
 */
public class OptionalTest {
	
	/**
	 * Optional实例的创建
	 */
	@Test
	public void test1() {
		Optional<Employee> op = Optional.of(new Employee());
		
//		Optional<Object> op2 = Optional.of(null); //这行会抛出NEP
		
		Employee employee = op.get();
		System.out.println(employee);
	}
	
	@Test
	public void test2() {
		Optional<Employee> op = Optional.ofNullable(new Employee());
		Employee employee = op.get();
		System.out.println(employee);
	}
	
	@Test
	public void test3() {
		Optional<Employee> op = Optional.ofNullable(null);
		if(op.isPresent()) {
			Employee employee = op.get();
			System.out.println(employee);
		}else {
			System.out.println("Null");
			Employee emp = op.orElse(new Employee("默认用户", 19, 99999)); //没有值返回一个默认值
			System.out.println(emp);
		}
	}
	
	@Test
	public void test4() {
		Optional<Employee> op = Optional.ofNullable(null);
		Employee emp = op.orElseGet(() -> new Employee("默认用户", 19, 99999));
		System.out.println(emp);
	}
	
	@Test
	public void test5() {
		Optional<Employee> op = Optional.ofNullable(new Employee("默认用户", 19, 99999));
		Optional<String> name = op.map(e -> e.getName()); //将容器中的对象应用到函数里面
		System.out.println(name);
	}
	
	@Test
	public void test6() {
		Optional<Employee> op = Optional.ofNullable(new Employee("默认用户", 19, 99999));
		//注意和上面的map的区别，map返回一个String类型就可以了，但是flatMap必须要求返回一个Optional
		Optional<String> name = op.flatMap(e -> Optional.of(e.getName()));
		System.out.println(name);
	}
	
	@Test
	public void test7() {
//		Man man = new Man();
//		String name = getGodnessName(man);
//		System.out.println(name);
		
		Optional<NewMan> op = Optional.ofNullable(null);
		String name  = getGodnessName2(op);
		System.out.println(name);
	}
	
	public String getGodnessName2(Optional<NewMan> man) {
		return man.orElse(new NewMan())
				.getGodness().orElse(new Godness("苍老师"))
				.getName();
	}
	
	
	/**
	 * 获取男人中的女神的传统写法
	 * @param man
	 * @return
	 */
	public String getGodnessName(Man man) {
		if(man == null) {
			return "苍老师";
		}
		if(man.getGodness() == null) {
			return "苍老师";
		}
		return man.getGodness().getName();
	}
	
	
	

}
