package com.dragon.se.singleton;

/**
 * 懒汉式
 *    直接创建实例对象，不管是否需要这个对象都会创建，它的实现包含
 *    1、构造器私有化
 *    2、使用公共静态变量来对外提供实例
 */
public class Singleton1 {
	public static final Singleton1 INSTANCE = new Singleton1();
	private Singleton1() {
		
	}
}
