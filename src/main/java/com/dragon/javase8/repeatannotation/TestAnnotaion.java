package com.dragon.javase8.repeatannotation;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Java8提供了重复注解和类型注解
 * 1、重复注解
 *    要使用重复注解，必须要有一个重复注解的容器注解(如本例中的MyAnnotations)，在这个重复注解内需要指定要使用的注解数组
 *    并且在重复注解中要通过@Repeatable指定上面的容器注解类
 *    通过上面2步，就可以使用重复注解，如下面show()方法上面的注解
 * @author wanglei
 *
 */
public class TestAnnotaion {
	
	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		Class<TestAnnotaion> clazz =  TestAnnotaion.class;
		Method method = clazz.getMethod("show");
		
		MyAnnotation[] ma = method.getAnnotationsByType(MyAnnotation.class);
		
		for (MyAnnotation myAnnotation : ma) {
			System.out.println(myAnnotation.value());
		}
	}
	
	
	/**
	 * 这里就使用了重复注解
	 * 并且对参数value使用了类型参数注解
	 */
	@MyAnnotation("Hello")
	@MyAnnotation("World")
	public void show(@MyAnnotation("abc") String value) {
		
	}

}
