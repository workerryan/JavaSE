package com.dragon.concurrent;

import sun.misc.Unsafe;

public class ObjectMonitorTest {
    private static Object obj = new Object();
    private static Unsafe unsafe = UnsafeInstance.getInstance();

    //加锁
    public void method1(){
        unsafe.monitorEnter(obj);
    }

    public void method2(){
        //业务逻辑
        unsafe.monitorExit(obj);
    }

    public static void main(String[] args) {
        ObjectMonitorTest test = new ObjectMonitorTest();
        test.method1();
        //业务逻辑
        test.method2();;


        //业务逻辑
        unsafe.monitorExit(obj);
    }
}
