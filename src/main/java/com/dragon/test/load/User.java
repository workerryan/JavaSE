package com.dragon.test.load;

public class User {
    private int id;

    public User(int id) {
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("关闭资源, user " + id + " 即将被回收");
    }

    public void sout(){
        System.out.println("----------类开始加载----------");
    }
}
