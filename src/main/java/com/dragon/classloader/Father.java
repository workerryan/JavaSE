package com.dragon.classloader;

/**
 * 父类的初始化<clinit>
 * 1、j = method();
 * 2、父类的静态代码块
 * 
 * 父类的示例初始化方法<init>
 * 1、super()方法
 * 2、i = test(); 这里实际执行的是子类的test()方法，因为被覆写了。多态
 * 3、子类的非静态代码块
 * 4、子类的午餐构造器（最后）
 */
public class Father {
	private int i = test();
	private static int j = method();

	static {
		System.out.print("(1)");
	}
	Father(){
		System.out.print("(2)");
	}
	{
		System.out.print("(3)");
	}
	
	public int test() {
		System.out.print("(4)");
		return 1;
	}
	public static int method() {
		System.out.print("(5)");
		return 1;
	}
}
