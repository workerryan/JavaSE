package com.dragon.jmm;

import java.util.concurrent.TimeUnit;

/**
 * JMM可见性
 */
public class VolatileVisibilitySample {
    private boolean initFlag = false;
    static Object obj = new Object();
    int i = 0;


    public void refresh(){
        this.initFlag = true;
        String threadName = Thread.currentThread().getName();
        System.out.println("线程 " + threadName + ": 修改共享变量initFlag");
    }

    public void load(){
        String threadName = Thread.currentThread().getName();

        while (!initFlag){
            synchronized (obj){
                //i++;
            }
        }
        System.out.println("线程 " + threadName + ": 嗅探到initFlag的状态改变");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileVisibilitySample sample = new VolatileVisibilitySample();
        Thread threadA = new Thread(() -> sample.refresh(), "threadA");
        Thread threadB = new Thread(() -> sample.load(), "threadB");

        threadB.start();
        TimeUnit.SECONDS.sleep(1);
        threadA.start();
    }
}