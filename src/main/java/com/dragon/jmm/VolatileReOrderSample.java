package com.dragon.jmm;

/**
 * 指令重排
 */
public class VolatileReOrderSample {
    private static int x = 0, y = 0;
    private static volatile int a = 0, b = 0;
    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;){
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread t1 = new Thread(() -> {
                //t1先启动，这么这句让它等待线程t2，以便t1和t2可以"同时"启动
                shortWait(10000);
                a = 1;  //代码[1]
                x = b;  //代码[2]
            });
            Thread t2 = new Thread(() -> {
                b = 1;  //代码[3]
                y = a;  //代码[4]
            });

            t1.start();
            t2.start();
            t1.join(); //加入主流程
            t2.join();

            System.out.println("第 " + i + " 次循环， x = " + x + " , y = " + y);
            if(x == 0 && y == 0){
                System.exit(0);
            }
        }
    }

    private static void shortWait(int interval) {
        long start = System.nanoTime();
        long end;
    }
}
