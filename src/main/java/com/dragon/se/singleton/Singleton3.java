package com.dragon.se.singleton;

import java.io.IOException;
import java.util.Properties;

/**
 * 静态代码块的饿汉式
 * 如果我们要通过静态代码块获取更多的信息，可以使用这种方式，在该类中，我们使用Properties获取配置文件里面的信息
 */
public class Singleton3 {
	public static final Singleton3 INSTANCE;
	private String info;
	static{
		try {
			Properties pro = new Properties();
			//这里使用的是类加载器，加载的是类路径下的single.properties文件
			pro.load(Singleton3.class.getClassLoader().getResourceAsStream("single.properties"));
			
			INSTANCE = new Singleton3(pro.getProperty("info"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		Singleton3 s = Singleton3.INSTANCE;
		System.out.println(s);
	}
	
	private Singleton3(String info){
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Singleton3 [info=" + info + "]";
	}
	
}
