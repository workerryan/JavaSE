package com.dragon.test;

import java.util.ArrayList;
import java.util.List;

public class HeapTest {
    byte[] a = new byte[100 * 1024];// 100KB

    public static void main(String[] args) throws InterruptedException {
        List<HeapTest> list = new ArrayList<>();
        for(;;){
            list.add(new HeapTest());
            Thread.sleep(100);
        }
    }
}

