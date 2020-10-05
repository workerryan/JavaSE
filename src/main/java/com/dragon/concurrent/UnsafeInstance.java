package com.dragon.concurrent;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 通过反射方式获取Unsafe对象
 */
public class UnsafeInstance {
    public static Unsafe getInstance(){
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        }
        catch (Exception e){
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }
}
