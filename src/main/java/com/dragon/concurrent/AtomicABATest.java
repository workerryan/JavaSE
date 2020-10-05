package com.dragon.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicABATest {
    static AtomicInteger atomic = new AtomicInteger(1);
    public static void main(String[] args) {
        Thread main = new Thread(() -> {
            int originalValue = atomic.get();
            System.out.println("操作线程 " + Thread.currentThread().getName() + " 修改前的数值 " + originalValue);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean success = atomic.compareAndSet(originalValue, 2);
            if(success){
                System.out.println("操作线程 " + Thread.currentThread().getName() + " CAS修改后的数值 " + atomic.get());
            }else{
                System.out.println("操作线程 " + Thread.currentThread().getName() + " CAS修改失败");
            }
        }, "主线程");

        Thread other = new Thread(() -> {
            atomic.incrementAndGet();  //自加
            System.out.println("操作线程 " + Thread.currentThread().getName() + " increase数值 " + atomic.get());
            atomic.decrementAndGet();  //自减
            System.out.println("操作线程 " + Thread.currentThread().getName() + " decrease数值 " + atomic.get());
        }, "干扰线程");

        main.start();
        other.start();
    }
}




















