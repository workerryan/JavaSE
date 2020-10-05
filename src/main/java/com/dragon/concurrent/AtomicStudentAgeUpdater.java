package com.dragon.concurrent;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicStudentAgeUpdater {
    //指定要修改哪个类的哪个属性
    static AtomicIntegerFieldUpdater aifu = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");

    private static final Unsafe unsafe = UnsafeInstance.getInstance();
    //获取age这个属性在student内存架构里面的偏移量，也就是student内存结构的开始到age字段的偏移量
    private static final long valueOffset;
    static{
        try {
            valueOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("age"));
            System.out.println("valueOffset = " + valueOffset);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("cloud not get offset");
        }
    }

    public static void main(String[] args) {
        Student student = new Student("张三", 10);
        //获取再加1，所以这里会打印10，由于aifu指定了要修改的是age属性，所以这里就是指的age属性
        System.out.println(aifu.getAndIncrement(student));
        //刚刚加了1，所以这里会打印11
        System.out.println(aifu.get(student));
        System.out.println(student.age);
        //通过unsafe类修改age的值，第1、2个参数分别代表要修改的是哪个对象，偏移量是多少
        //第三个参数是要修改的值，第四个参数是修改后的值
        unsafe.compareAndSwapInt(student, valueOffset, student.age, 18);
        System.out.println(student.age);
    }

    static class Student {
        private String name;
        //要通过原子类修饰，这里的必须是public volatile
        public volatile int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
