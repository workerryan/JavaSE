package com.dragon.javase8.streamapi;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.dragon.javase8.lambda.demo.Employee;
import com.dragon.javase8.lambda.demo.Employee.Status;
import com.dragon.javase8.streamapi.model.Trader;
import com.dragon.javase8.streamapi.model.Transaction;

/**
 * StreamApi的练习
 * @author wanglei
 *
 */
public class StreamApi2 {
	List<Transaction> transactions = null;
	
	@Before
	public void init() {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		transactions = Arrays.asList(
				new Transaction(raoul, 2011, 300),
				new Transaction(mario, 2012, 1000),
				new Transaction(alan, 2011, 400),
				new Transaction(brian, 2012, 710)
				);
	}
	
	/**
	 * 给定一个数字列表，任何返回一个由每个数的平方构成的列表呢？
	 * 给定【1,2,3,4,5】，应该返回[1,4,9,16,25]
	 */
	@Test
	public void test1() {
		Integer[] source = new Integer[] {1,2,3,4,5};
		Stream<Integer> stream = Arrays.stream(source);
		List<Integer> collect = stream.map(x -> x*x)
				.collect(Collectors.toList());
		System.out.println(collect);
	}
	
	/**
	 * 如何map和reduce方法数一数流中有多少个Employee
	 */
	@Test
	public void test2() {
		List<Employee> employees = Arrays.asList(
				new Employee("张三", 18, 9999.99, Status.BUSY),
				new Employee("李四", 38, 5559.55, Status.FREE),
				new Employee("王五", 50, 6666.66, Status.VOCATION),
				new Employee("赵六", 16, 2222.22, Status.BUSY),
				new Employee("田七", 17, 7777.77, Status.FREE),
				new Employee("陈八", 35, 1111.11, Status.VOCATION),
				new Employee("沈九", 19, 8888.88, Status.FREE)
				);
		Stream<Employee> stream = employees.stream();
		Optional<Integer> count = stream.map(e -> 1)
			.reduce(Integer :: sum);
		System.out.println(count.get());
	}
	
	//-------------------------------------------------------------------
	/**
	 * 找出2011年发生的所有交易，并按交易额排序(从低到高)
	 */
	@Test
	public void test3() {
		transactions.stream()
			.filter(t -> t.getYear() == 2011)   //过滤使用filter
			.sorted((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()))  //排序使用sorted
			.forEach(System.out :: println);
	}
	
	/**
	 * 交易员都在哪些不同的城市工作过？
	 */
	@Test
	public void test4() {
		transactions.stream()
			.map(t -> t.getTrade().getCity())	//提取所有的城市
			.distinct()                          //去重
			.forEach(System.out :: println);
	}
	
	/**
	 * 查找所有来自剑桥的交易员，并按姓名排序
	 */
	@Test
	public void test5() {
		transactions.stream()
			.filter(t -> t.getTrade().getCity().equals("Cambridge")) //过滤来着剑桥的交易
			.map(Transaction :: getTrade)   //过滤得到交易员
			.sorted((t1, t2) -> t1.getName().compareTo(t2.getName())) //按姓名排序
			.forEach(System.out :: println);
	}

	/**
	 * 返回所有交易员的姓名字符串，按字母顺序排序
	 */
	@Test
	public void test6() {
		transactions.stream()
			.map(e -> e.getTrade().getName())
			.sorted()
			.forEach(System.out :: println);
		
	}
	
	/**
	 * 有没有交易员是在米兰工作的？
	 */
	@Test
	public void test7() {
		boolean anyMatch = transactions.stream()
			.anyMatch(t -> t.getTrade().getCity().equals("Milan"));
		System.out.println(anyMatch);	
		
	}
	
	/**
	 * 打印生活在剑桥的交易员的所有交易额
	 */
	@Test
	public void test8() {
		DoubleSummaryStatistics collect = transactions.stream()
			.filter(t -> t.getTrade().getCity().equals("Cambridge"))
			.collect(Collectors.summarizingDouble(Transaction :: getValue));
		System.out.println(collect.getSum() + "\n");
		
		Optional<Integer> sum = transactions.stream()
			.filter(t -> t.getTrade().getCity().equals("Cambridge"))
			.map(Transaction :: getValue)
			.reduce(Integer :: sum);
		System.out.println(sum.get());
		
	}
	/**
	 * 所有交易中，最高的交易额是多少
	 */
	@Test
	public void test9() {
		Optional<Integer> max = transactions.stream()
			.map(Transaction :: getValue)
			.max((t1, t2) -> Integer.compare(t1, t2));
		System.out.println(max.get());
		
		DoubleSummaryStatistics dss = transactions.stream()
				.collect(Collectors.summarizingDouble(Transaction :: getValue));	//收集
		System.out.println(dss.getMax());
	}
	
	/**
	 * 找到交易额最小的交易
	 */
	@Test
	public void test10() {
		Optional<Transaction> min = transactions.stream()
			.min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
		System.out.println(min.get());
	}
}
