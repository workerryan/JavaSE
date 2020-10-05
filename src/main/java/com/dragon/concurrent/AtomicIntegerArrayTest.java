package com.dragon.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {
    static int[] array = new int[]{1,2};
    static AtomicIntegerArray aiArray = new AtomicIntegerArray(array);

    public static void main(String[] args) {
        aiArray.getAndSet(0, 3);
        System.out.println(aiArray.get(0));
        System.out.println(array[0]);
    }
}
