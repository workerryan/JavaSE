package com.dragon.juc;
import java.util.concurrent.TimeUnit;

/**
 * volatile的可见性
 * @author wanglei
 * @since 1.0.0
 */
public class VolatileDemo1 {
    public static void main(String[] args) {
        MyData myData = new MyData();

        new Thread(() -> {
            //3秒后修改number为60
            System.out.println(Thread.currentThread().getName() + " come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //代码[1]
            myData.addT060();
            System.out.println(Thread.currentThread().getName() + " updated number value : " + myData.number);
        }, "aaa").start();

        //这里是一个循环，如果读取到myData.number为0，那么一直在这里等待
        while (myData.number == 0){

        }
        //当myData.number值修改以后，那么将会打印这句话
        System.out.println(Thread.currentThread().getName() + " mission is over");
    }
}

class MyData{
    volatile int number = 0; //增加了volatile关键字
    public void addT060() {
        this.number = 60;
    }
}