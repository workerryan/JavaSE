package com.dragon.javase8.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;
import com.dragon.javase8.lambda.demo.Employee.Status;

/**
 * 一、Stream的三大操作步骤
 * 1、创建Stream  见test1()
 * 2、中间操作   
 *     2.1、筛选与切片 见test2() ~ test5()
 *     2.2、映射 见test6() ~ test7()
 *     2.3、排序 见test8()
 * 3、终止操作
 *     3.1、查找与匹配 见test9()
 *     3.2、规约(reduce)  test10()
 *     3.3、收集(collect)  见test11() ~ test15()
 * @author wanglei
 *
 */
public class StreamApi1 {
	List<Employee> employees = Arrays.asList(
			new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 5559.55),
			new Employee("王五", 50, 6666.66),
			new Employee("赵六", 16, 2222.22),
			new Employee("田七", 17, 7777.77),
			new Employee("陈八", 35, 1111.11),
			new Employee("沈九", 19, 8888.88)
			);
	
	
	List<Employee> employees2 = Arrays.asList(
			new Employee("张三", 18, 9999.99, Status.BUSY),
			new Employee("李四", 38, 5559.55, Status.FREE),
			new Employee("王五", 50, 6666.66, Status.VOCATION),
			new Employee("赵六", 16, 2222.22, Status.BUSY),
			new Employee("田七", 17, 7777.77, Status.FREE),
			new Employee("陈八", 35, 1111.11, Status.VOCATION),
			new Employee("沈九", 19, 8888.88, Status.FREE)
			);
	
	//创建Stream
	@Test
	public void test1() {
		//1、可以通过Collection系列集合提供的stream()获取一个串行流
		//   或者通过parallelStream()获取一个并行流
		List<String> list = new ArrayList<>();
		Stream<String> stream1 = list.stream();
		
		//2、通过Arrays中的静态方法stream()获取数组流
		Employee[] emps = new Employee[10];
		Stream<Employee> stream2 = Arrays.stream(emps);
		
		//3、通过Stream的静态方法
		Stream<String> stream3 = Stream.of("aa", "bb");
		
		//4、创建无限流
		//4.1、迭代
		Stream<Integer> stream4 = Stream.iterate(0 , x -> x + 2);
		stream4.limit(10).forEach(System.out :: println);
		//4.2、生成
		Stream.generate(() -> Math.random())
			.forEach(System.out::println);
	}
	
	/**
	 * 流的中间操作。筛选与切片 。见test2() ~ test5()
	 * filter : 接收Lambda，从流中排出某些原生  见test2()
	 * limit : 截断流，使其元素不超过给定数量    见test3()
	 * skip(n) : 跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补   见test4()
	 * distinct : 筛选，通过流所生成元素的hashCode()和equals()去除重复元素  见test5()
	 */
	@Test
	public void test2() {
		//中间操作
//		Stream<Employee> stream = employees.stream()
//			.filter(e -> e.getAge() > 35); //过滤年龄大于35的
		//终止操作
//		stream.forEach(System.out :: println);
		
		
		//中间操作不会执行任何的操作，只有当终止操作后，中间操作的内容才会一次性执行完毕，比如下面的代码将会打印：
//		==> 执行中间操作
//		==> 执行中间操作
//		Employee [name=李四, age=38, salary=5559.55]
//		==> 执行中间操作
//		Employee [name=王五, age=50, salary=6666.66]
//		==> 执行中间操作
//		==> 执行中间操作
//		==> 执行中间操作
//		==> 执行中间操作
//		从上面打印可以看出，在执行.filter()的时候执行了一个迭代，这个迭代是由Stream API完成的，这种迭代也叫内部迭代
//		
		Stream<Employee> stream2 = employees.stream()
				.filter(e -> {
						System.out.println("==> 执行中间操作");
						return e.getAge() > 35;
					}); //过滤年龄大于35的
		//终止操作：一次性执行全部内容，即“惰性求值”
		stream2.forEach(System.out :: println);
	}
	
	/**
	 * 该方法的打印结果为
	 短路
	 Employee [name=张三, age=18, salary=9999.99]
	 短路
	 Employee [name=李四, age=38, salary=5559.55]
	 从上面的打印结果可以看到，只迭代了2次，所以limit()可以起到截断的作用
	 */
	@Test
	public void test3() {
		Stream<Employee> stream = employees.stream();
		stream.filter(e -> { 
				System.out.println("短路");
				return e.getSalary() > 5000;
			})
			.limit(2) // 截断流
			.forEach(System.out::println);
	}
	
	@Test
	public void test4() {
		Stream<Employee> stream = employees.stream();
		stream.filter(e -> { 
			return e.getSalary() > 5000;
		})
		.skip(2) 
		.forEach(System.out::println);
	}
	
	@Test
	public void test5() {
		List<Employee> employees2 = Arrays.asList(
				new Employee("张三", 18, 9999.99),
				new Employee("李四", 18, 5559.55),
				new Employee("王五", 50, 6666.66),
				new Employee("赵六", 16, 2222.22),
				new Employee("田七", 18, 7777.77),
				new Employee("陈八", 35, 1111.11),
				new Employee("沈九", 18, 8888.88)
				);
		Stream<Employee> stream = employees2.stream();
		stream.filter(e -> { 
			return e.getAge() > 17;
		})
		.distinct()	//通过流所生成元素的hashCode()和equals()去除重复元素
		.skip(2) 
		.forEach(System.out::println);
	}
	
	/**
	 * 映射 见test6() ~ test7()
	 * map - 接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
	 * flatMap - 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	@Test
	public void test6() {
		List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");
		list.stream()
			.map((str) -> str.toUpperCase())
			.forEach(System.out::println);
		System.out.println("----------------------");
		
		employees.stream()
		.map(Employee :: getName)	//提取name属性
		.forEach(System.out :: println);
	}
	@Test
	public void test7() {
		List<Order> orders = Arrays.asList(
				new Order("1111", "111111", "111111", "11111111"),
				new Order("1111", "222222", "222222", "22222222"),
				new Order("1111", "333333", "333333", "33333333"),
				new Order("3940", "444444", "444444", "44444444"),
				new Order("1111", "555555", "555555", "55555555"),
				new Order("5555", "666666", "666666", "66666666"),
				new Order("1111", "777777", "777777", "77777777")
				);
		orders.stream()
			.map(order -> {
				Integer salary = Integer.valueOf(order.getOnlineTime()),
						vedioMoney = Integer.valueOf(order.getVideoMoney()),
						enjoyMoney = Integer.valueOf(order.getEnjoyMoney()),
						imMoney = Integer.valueOf(order.getImMoney());
				order.setOnlineTime(  salary/3600 + "时" + (salary%3600)/60 + "分" + salary%60 + "秒");
				order.setVideoMoney(vedioMoney/100 + "." + vedioMoney%100);
				order.setEnjoyMoney(enjoyMoney/100 + "." + enjoyMoney%100);
				order.setImMoney(imMoney/100 + "." + imMoney%100);
				return order;
			}).collect(Collectors.toList());
		for(Order order : orders) {
			System.out.println(order);
		}
	}
	
	/**
	 * 排序 
	 * sorted()  自然排序(按照Comparable接口来进行排序，实际上是按照实现了Comparable接口的compareTo()方法来进行排序)
	 * sorted(Comparator com)  定制排序(Comparator)
	 */
	@Test
	public void test8() {
		List<String> list = Arrays.asList("iii","ggg","fff","aaa","bbb","ccc","ddd");
		list.stream().sorted().forEach(System.out :: println);
		System.out.println("---------------");
		
		employees.stream().sorted((e1, e2) -> {
			if(e1.getAge() == e2.getAge()) {
				return e1.getName().compareTo(e2.getName());
			}else {
				return e1.getAge() > e2.getAge() ? 1: (e1.getAge() == e2.getAge() ? 0 : -1);
			}
		}).forEach(System.out::println);
		
	}
	
	/**
	 * 查找与匹配
	 * allMatch  - 检查是否匹配所有元素
	 * anyMatch  - 检查是否至少匹配一个元素
	 * noneMatch - 检查是否没有匹配所有元素
	 * findFirst - 返回第一个元素
	 * findAny   - 返回当前流中的任意元素
	 * count     - 返回流中元素的总个数
	 * max       - 返回流中的最大值
	 * min       - 返回流中最小值
	 */
	@Test
	public void test9() {
		boolean b1 = employees2.stream()
			.allMatch(e -> e.getStatus().equals(Status.BUSY));	//是否所有的员工都是BUSY状态
		System.out.println(b1);
		
		boolean b2 = employees2.stream()
			.anyMatch(e -> e.getStatus().equals(Status.BUSY));    //是否有一个员工的状态是BUSY状态
		System.out.println(b2);
		
		boolean b3 = employees2.stream()
				.noneMatch(e -> e.getStatus().equals(Status.BUSY));  
		System.out.println(b3);
				
		Optional<Employee> emp = employees2.stream()
			.sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())) //按照工资升序排序
			.findFirst(); //获取第一个
		System.out.println(emp.get());
		
		Optional<Employee> emp2 = employees2.stream() //获取一个串行流
			.filter(e -> e.getStatus().equals(Status.FREE))  //过滤出状态为FREE的用户
			.findAny();	//随机获取一个
		System.out.println(emp2.get());
		
		Optional<Employee> emp3 = employees2.parallelStream() //获取一个并行流
				.filter(e -> e.getStatus().equals(Status.FREE))  //过滤出状态为FREE的用户
				.findAny();	//随机获取一个
			System.out.println(emp3.get());
		/* stream()是获取一个串行流，串行流的意思就是先过滤状态为FREE，再随机获取一个
		 * parallelStream()是获取一个并行流，并行流是意思是多个线程同时获取状态为FREE并且随机获取一个，哪个线程先找到，就返回该线程的内容
		 * */	
		
		long count = employees2.stream().count();
		System.out.println(count);
			
		Optional<Employee> max = employees2.stream()
				.max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));	 //获取薪水最高的
		System.out.println(max.get());
		
		Optional<Employee> min = employees2.stream()
				.min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));	 //获取薪水最低的
		System.out.println(min.get());
		
		Optional<Double> min2 = employees2.parallelStream()
			.map(Employee :: getSalary)	//先过滤工资
			.min(Double :: compare);		//按工资排序并且获取最小工资
		System.out.println(min2.get());
	}
	
	/**
	 * 规约 - 可以将流中元素反复结合起来，得到一个值。
	 * T reduce(T identity, BinaryOperator<T> accumulator);  第一个参数是起始值，第二个参数是一个二元运算的Function
	 * Optional<T> reduce(BinaryOperator<T> accumulator);
	 * <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);
	 */
	@Test
	public void test10() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		/* reduce(T identity, BinaryOperator<T> accumulator);的运算操作是从identity这个起始值开始的。
		 * 它将起始值作为x，然后从流中取出一个元素作为y，然后进行运算(这里的运算就是加法)。
		 * 然后将运行结果作为x，再从流中取出一个值作为y，然后再进行运算，直到流中没有值。
		 * 这其实就相当于是一个递归操作
		 * */
		Integer sum = list.stream()
			.reduce(0, (x , y) -> x  + y);	//计算累加的和
		
		System.out.println(sum + "\n");
		
		//计算工资总和，先用map进行提取，reduce进行操作
		Optional<Double> optional = employees.stream()
			.map(Employee :: getSalary)
			.reduce(Double :: sum);
		System.out.println(optional.get());
		
	}
	
	
	/**
	 * 收集 
	 * collect - 将流转换为其他形式。接收一个Collection接口的实现，用于给Stream中元素做汇总的方法
	 */
	@Test
	public void test11() {
		//提取集合中的名字，并且赋值到集合中去
		List<String> list = employees.parallelStream()
			.map(Employee :: getName)
			.collect(Collectors.toList()); //接收一个Collector，表示想按照什么样的方法进行收集
								//Collector是一个接口，Collector接口中方法的实现决定了如果对流执行收集操作(如收集到List、Set、Map)。
								//但是Collectors实现类提供了很多静态方法，可以方便地创建常见收集器实例。
							    //Collectors.toList()、Collectors.toSet()、 Collectors.toMap()分别是搜集到list、set、map中
		list.forEach(System.out :: println);
		
		System.out.println("-------------------------");
		HashSet<String> collect = employees.stream()
			.map(Employee :: getName)
			.collect(Collectors.toCollection(HashSet :: new));  //收集到HashSet中(收集到指定集合中)
		collect.forEach(System.out :: println);
	}
	
	//计算
	@Test
	public void test12() {
		 Long count = employees.parallelStream()
		 	.collect(Collectors.counting());//统计总数
		 System.out.println("总数:"+count);
		 
		 Double avg = employees.parallelStream()
		 	.collect(Collectors.averagingDouble(Employee :: getSalary));
		System.out.println("工资平均值:" + avg);
		
		Double sum = employees.stream()
			.collect(Collectors.summingDouble(Employee :: getSalary));
		System.out.println("工资总和：" + sum);
		
		Optional<Employee> max = employees.stream()
			.collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		System.out.println("最高工资: " + max.get());
		
		Optional<Double> min = employees.stream()
			.map(Employee :: getSalary)
			.collect(Collectors.minBy(Double :: compare));
		System.out.println("最低工资: " + min.get());
		
		//收集的另一种写法
		DoubleSummaryStatistics dss = employees.stream()
			.collect(Collectors.summarizingDouble(Employee :: getSalary));	//收集
		System.out.println(dss.getAverage());	//取平均值
		System.out.println(dss.getCount());		//总数
		System.out.println(dss.getMax());		//最大值
		System.out.println(dss.getMin());		//最小值
		System.out.println(dss.getSum());		//总和
	}
	
	//分组
	@Test
	public void test13() {
//		安装状态进行分组
//		employees.stream()
//			.collect(Collectors.groupingBy(e -> {
//				return e.getStatus();
//			}));
//		employees.stream()
//			.collect(Collectors.groupingBy(e ->  e.getStatus()));
		//上面的2个方法等价下面的
		Map<Status, List<Employee>> collect = employees2.stream()
			.collect(Collectors.groupingBy(Employee :: getStatus));
		System.out.println(collect);
		
		//多级分组
		Map<Status, Map<String, List<Employee>>> collect2 = employees2.stream()
			.collect(Collectors.groupingBy(Employee :: getStatus, 	//按照状态分组
					Collectors.groupingBy(  e -> {					//按照年龄进行分组
						Employee emp = (Employee)e;
						if(e.getAge() <= 35) {
							return "青年";
						}else if(emp.getAge() <= 50) {
							return "中年";
						}else {
							return "老年";
						}
					})));
		System.out.println(collect2);
		
	}
	
	
	
	//分区，分区是指分为true和false。满足条件是true，不满足条件是false
	@Test
	public void test14() {
		Map<Boolean, List<Employee>> collect = employees2.stream()
			.collect(Collectors.partitioningBy(e -> e.getSalary() > 6666));
		System.out.println(collect);
	}
	
	//链接
	@Test
	public void test15() {
		String str = employees2.stream()
			.map(Employee :: getName)
			.collect(Collectors.joining(",", "-->", "<--"));	//以逗号链接名字
		System.out.println(str); //打印结果为  -->张三,李四,王五,赵六,田七,陈八,沈九<--
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
