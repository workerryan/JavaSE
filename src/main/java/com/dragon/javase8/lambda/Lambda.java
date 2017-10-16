package com.dragon.javase8.lambda;

import java.util.Comparator;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * Lambda 是一个匿名函数，我们可以把Lambda表达式理解为是一段可以传递的代码(将代码像数据一样进行传递)。可以写出更简洁、更灵活的代码。
 * Lambda 表达式在Java语言中引入了一个新的语法元素和操作符。这个操作符为 “->” ，该操作符被称为Lambda操作符或剪头操作符。它将Lambda分为两个部分:
        左侧:指定了Lambda表达式需要的所有参数 
        右侧:指定了Lambda体，即Lambda表达式要执行的功能
        匿名函数一般是对接口的实现，比如说IEmployeeFilter这个接口，里面有一个方法，public boolean filterEmployee(T t)。
        那么使用Lambda表达式，左侧的参数就是filterEmployee(T t)这个方法的参数，那么相应的Lambda的右侧就是对这个方法的实现功能。
        所以Lambda表达式默认就是找的接口的实现
 * 要想使用Lambda表达式，需要函数式接口的支持      
 * Lambda表达式的基础语法  ：
 * 1、无参，无返回值 ，见 test1()
 * 2、有一个参数并且无返回值 , 见test2()
 * 3、有一个参数并且无返回值，那么可以省略小括号，见test3()
 * 5、有多个参数并且返回值，并且Lambda体中有多条语句，见test4()
 * 5、当 Lambda 体只有一条语句时，return与大括号可以省略，见test5()
 * 5、Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器可以通过上下文推断出数据类型，这儿推断数据类型叫“类型推断” 见test6()
 * 
 * 总结:
 * 上联 : 左右遇一括号省 -> 就是说左边参数只有一个,那么左侧的小括号可以省略；右边Lambda体只有一条语句，那么右边的大括号可以省略
 * 下联 : 左侧推断类型省 -> 就是说箭头操作符的左侧有个类型推断，可以通过目标上下文推断出参数类型
 * 横批 : 能省则省
 * 
 * Lambda表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法时，这个接口就是函数式接口。函数式接口可以使用注解@FunctionalInterface修饰
 *     注解@FunctionalInterface可以检查是否是函数式接口
 * @author wanglei
 *
 */
public class Lambda {

	/**
	 * 无参、无返回值的Lambda
	 */
	@Test
	public void test1() {
		//传统方式的匿名内部类
		int num = 0; //JDK1.7之前，要想在匿名内部类使用该变量，必须声明为final，1.8之后不需要显式声明了，但是其实还是是final类型的
		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello world!" + num);
			}
		};
		r.run();
		
		//Runnable接口里面的方法是无参数的，所以-> 左侧是个小括号表示无参，
		//右侧是一句代码，这句代码其实就是上面的public void run(){}中的大括号方法体中的内容
		Runnable r2 = () -> System.out.println("Hello Lambda" + num);
		r2.run();
//		num ++; 加上该句代码，上面使用到num的地方就会报错
	}
	

	@Test
	public void test2() {
		Consumer<String> fun = (arg) -> System.out.println(arg);
		fun.accept("三石哥威武");
		/*Consumer<T>接口只有一个方法 void accept(T t);上面的Consumer<String> fun = (arg) -> System.out.println(arg);
		 * 代码声明了一个fun，然后后面调用fun.accept("三石哥威武");就会执行上面的方法，只不过方法体还是是一个打印，打印的是arg这个参数
		 * 
		 * */
	}
	
	@Test
	public void test3() {
		Consumer<String> fun = arg -> System.out.println(arg);
		fun.accept("三石哥威武");
	}
	
	/**
	 * Lambda体有多条语句，必须是个大括号
	 */
	@Test
	public void test4() {
		Comparator<Integer> com = (x,y) -> {
			System.out.println("执行比较方法");
			return Integer.compare(x, y);
		};
	}
	
	@Test
	public void test5() {
		Comparator<Integer> com = (x,y) -> Integer.compare(x, y);
	}
}
