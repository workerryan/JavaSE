package com.dragon.se.singleton;

/*
 * 在内部类被加载和初始化时，才创建INSTANCE实例对象
 * 静态内部类不会自动随着外部类的加载和初始化而初始化，它是要单独去加载和初始化的。
 * 因为是在内部类加载和初始化时，创建的，因此是线程安全的
 */
public class Singleton7 {
	private Singleton7() {}

	public static class Inner{
		private static final Singleton7 instance = new Singleton7();
	}
	
	public static Singleton7 getInstance() {
		return Inner.instance;
	}
}
