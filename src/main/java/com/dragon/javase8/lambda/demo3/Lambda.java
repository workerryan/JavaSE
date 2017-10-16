package com.dragon.javase8.lambda.demo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * Java8内置的4大函数式接口
 * 
 * Consumer<T>     消费型接口
 *     void accept(T t);  对传递的参数t进行操作，没有返回值
 * Supplier<T>     供给型接口
 *     T get();           
 * Function<T, R>  函数型接口
 *     R apply(T t);
 * Predicate<T>    断言型接口
 *     boolean test(T t)
 * @author wanglei
 *
 */
public class Lambda {
	//Consumer<T>  消费型接口
	@Test
	public void test1() {
		happy(100D, x -> System.out.println("x"));
	}
	//利用Consumer接口对money进行消费，具体的逻辑是在调用时处理的
	private void happy(double money, Consumer<Double> con) {
		con.accept(money);
	}
	
	//Supplier<T>     供给型接口
    @Test
    public void test2() {
    	    List<Integer> list = getNumList(5, () -> new Random().nextInt(500));
    	    for(Integer i : list) {
    	    		System.out.println(i);
    	    }
    }
    //利用供给型接口产生指定个数一些数，并且放到集合里面
    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
    		List<Integer> list = new ArrayList<Integer>(num);
    		for(int i = 0 ; i < num; i++){
    			list.add(supplier.get());
    		}
    		return list;
    }
    
    //Function<T, R>  函数型接口
    @Test
    public void test3() {
    		String str = caseUpper("abcd", (x) -> x.toUpperCase());
    		System.out.println(str);
    }
    private String caseUpper(String string, Function<String, String> function) {
    		return function.apply(string);
    }
    
    //Predicate<T>    断言型接口
    @Test
    public void test4() {
    		List<String> list = Arrays.asList("hello", "world", "zhang", "san", "li", "si");
    		List<String> result = filterStr(list, (x) -> x.length() > 3);
    		for(String str : result) {
    			System.out.println(str);
    		}
    }
    //将满足条件的字符串加入到集合
    public List<String> filterStr(List<String> list, Predicate<String> pre){
    		List<String> strList = new ArrayList<>();
    		for(String str : list){
    			if(pre.test(str)) {
    				strList.add(str);
    			}
    		}
    		return strList;
    }
}
