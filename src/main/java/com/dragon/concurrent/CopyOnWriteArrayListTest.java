package com.dragon.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList / CopyOnWriteArraySet
 * 这2个类在添加操作多时，效率低，因为每次添加时都会进行复制，开销会非常大。并发迭代操作多时，可以选择
 * @author wanglei
 *
 */
public class CopyOnWriteArrayListTest {
	public static void main(String[] args) {
		HelloThread ht = new HelloThread();
		for (int i = 0; i < 10; i++) {
			new Thread(ht).start();  
		}
	}
}


class HelloThread implements Runnable {
	//之前的写一个线程安全的List的写法，通过Collections.synchronizedList将ArrayList里面的方法加上了synchronized
//	private static List<String> list = Collections.synchronizedList(new ArrayList<String>());
	
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
	
	static {
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
	}
	@Override
	public void run() {
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
			list.add("ddd");
		}
	}
}