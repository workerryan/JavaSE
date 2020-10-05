package com.dragon.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private volatile int count;
    private Lock lock = new ReentrantLock(true);

    private void modifyResources(String threadName){
        System.out.println(threadName + " 进入");
        lock.lock();
        System.out.println(threadName + " 第一次获取资源");
        count++;
        lock.lock();
        System.out.println(threadName + " 第二次获取资源");
        count ++;
        lock.unlock();
        System.out.println(threadName + " 释放第一个锁");

        lock.unlock();
        System.out.println(threadName + " 释放第二个锁");
    }

    public static void main(String[] args){
        Test test = new Test();

        new Thread(() -> test.modifyResources(Thread.currentThread().getName()),"t1").start();
        new Thread(() -> test.modifyResources(Thread.currentThread().getName()),"t2").start();
    }
}
