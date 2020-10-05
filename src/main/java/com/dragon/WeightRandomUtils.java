package com.dragon;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightRandomUtils {
    private static AtomicInteger choose = new AtomicInteger();
    private static Random random = new Random();
    //3台机器
    private final static int m = 3;

    //构造加权的机器，a的权值是2，b的权值是3，c的权值是5
    private static Map<Integer, String> map = new HashMap(10);
    static {
        map.put(0, "a");
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "b");
        map.put(4, "b");
        map.put(5, "c");
        map.put(6, "c");
        map.put(7, "c");
        map.put(8, "c");
        map.put(9, "c");
    }

    public static void main(String[] args) {
        int a = 0, b = 0, c = 0;

        //模拟10次请求
        for (int i = 0; i < 20; i++) {
            int number = choose.addAndGet(1);
            if(number == m){
                choose.set(0);
            }
            int index = random.nextInt(map.size());
            String target = map.get(index);
            System.out.println(target);
            if("a".equals(target)){
                a++;
            }else if("b".equals(target)){
                b++;
            }else{
                c++;
            }
        }
        System.out.println("a = " + a + " , b = " + b + " , c = " + c);

//        System.out.println(Objects.equals(null, "a"));
//        System.out.println(Objects.equals(null, null));
//        System.out.println(Objects.equals("a", null));

        String str = null;
        System.out.println("a".equals(str));
        System.out.println(str.equals("a"));
    }
}
