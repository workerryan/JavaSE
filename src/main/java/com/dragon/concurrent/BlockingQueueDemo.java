package com.dragon.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
		
		System.out.println("插入开始： " + sdf.format(new Date()));
		System.out.println(queue.offer("a", 2, TimeUnit.SECONDS));
		System.out.println(queue.offer("c", 2, TimeUnit.SECONDS));
		System.out.println(queue.offer("c", 2, TimeUnit.SECONDS));
		System.out.println(queue.offer("x", 2, TimeUnit.SECONDS));
		System.out.println("插入结束： " + sdf.format(new Date()));
		
		System.out.println("获取开始： " + sdf.format(new Date()));
		System.out.println(queue.poll(2, TimeUnit.SECONDS));
		System.out.println(queue.poll(2, TimeUnit.SECONDS));
		System.out.println(queue.poll(2, TimeUnit.SECONDS));
		System.out.println(queue.poll(2, TimeUnit.SECONDS));
		System.out.println("获取结束： " + sdf.format(new Date()));
	}
}
