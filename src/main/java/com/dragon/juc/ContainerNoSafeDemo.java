package com.dragon.juc;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ContainerNoSafeDemo {
	public static void main(String[] args) {
		Set<String> list = new CopyOnWriteArraySet<>();
		 
		for (int i = 0; i < 30; i++) {
			new Thread(() ->  {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(Thread.currentThread().getName() + list);
			}, String.valueOf(i)).start();
		}
	}

	private static void listSafe() {
		List<String> list = new CopyOnWriteArrayList<>();
		//
		for (int i = 0; i < 30; i++) {
			new Thread(() ->  {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(Thread.currentThread().getName() + list);
			}, String.valueOf(i)).start();
		}
	}
}
