package com.dragon.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁编写的一个缓存
 * @author wanglei
 *
 */
public class ReadWriteLockCacheDemo {
	public static void main(String[] args) {
		MyCache chche = new MyCache();
		
		for (int i = 0; i < 5; i++) {
			final String temp = String.valueOf(i);
			new Thread(() -> {
				chche.put(temp, temp);
			}, temp).start();
		}
		
		for (int i = 0; i < 5; i++) {
			final String temp = String.valueOf(i);
			new Thread(() -> {
				Object value = chche.get(temp);
			}, temp).start();
		}
	}
}

class MyCache {
	//使用volatile保证可见性
	private volatile Map<String, Object> map = new HashMap<>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void put(String key, Object value) {
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 正在写入 " + key);
			//模拟写的延迟
			TimeUnit.MICROSECONDS.sleep(300); 
			map.put(key, value);
			System.out.println(Thread.currentThread().getName() + "\t 写入完成 ");
		}catch(Exception e) {
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	public Object get(String key) {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t 正在读取 " + key);
			Object value = map.get(key);
			System.out.println(Thread.currentThread().getName() + "\t 读取完成 " + value);
			return value;
		}catch(Exception e) {
			return null;
		}finally {
			lock.readLock().unlock();
		}
	}
}