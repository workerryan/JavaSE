package com.dragon.juc;

        import java.util.concurrent.TimeUnit;

/**
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
            myData.addT060();
            System.out.println(Thread.currentThread().getName() + " updated number value : " + myData.number);
        }, "aaa").start();

        while (myData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + " mission is over");
    }
}

class MyData{
    int number = 0;
    public void addT060() {
        this.number = 60;
    }
}