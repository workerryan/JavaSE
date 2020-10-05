package com.dragon.juc;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
	 public static void main(String[] args) {
		 AtomicInteger atomic = new AtomicInteger(5);
		 //main do thing ...
		 System.out.println(atomic.compareAndSet(5, 2019) + "\t current data :" + atomic.get());

		 System.out.println(atomic.compareAndSet(5, 1024) + "\t current data :" + atomic.get());
	 }
}
