package com.dragon.jmm;

import java.util.concurrent.TimeUnit;

public class VolatileAtomicSample {
    private volatile static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    //counter ++;
                    counter = counter + 1 ;
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println(counter);
    }
}
