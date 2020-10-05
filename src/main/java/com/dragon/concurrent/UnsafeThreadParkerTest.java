package com.dragon.concurrent;

import java.util.concurrent.locks.LockSupport;

public class UnsafeThreadParkerTest {
    public static void main(String[] args) throws InterruptedException {
        /*Thread t = new Thread(() -> {
            System.out.println("thread running...");
            LockSupport.park(); //线程阻塞
            System.out.println("thread is over ");
        });
        t.start();

        Thread.sleep(1000);

        //唤醒指定的线程
        LockSupport.unpark(t);
        System.out.println("main thread is over");*/

        LockSupport.unpark(Thread.currentThread());
        System.out.println("step 1");
        LockSupport.park();
        System.out.println("main over");
    }
}
