package com.dragon.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArrayBlockingQueueDemo {
    private BlockingQueue<Ball> queue = new ArrayBlockingQueue<>(1);
    
    /** 生产者 */
    public void produce(Ball ball) throws InterruptedException {
        queue.put(ball);
    }

    /** 消费者*/
    public Ball consumer() throws InterruptedException {
        return queue.take();
    }

    public int size(){
        return queue.size();
    }
    
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        ArrayBlockingQueueDemo box = new ArrayBlockingQueueDemo();

        pool.execute(() -> {
            for(;;){
                try {
                    Ball ball = new Ball("生产者1");
                    System.out.println("生产者1往箱子放入球 " + ball.id);
                    box.produce(ball);
                    System.out.println("生产者1查询箱子里面有球 " + box.size() + " 个");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(() -> {
            for(;;){
                try {
                    Ball ball = new Ball("生产者2");
                    System.out.println("生产者2往箱子放入球 " + ball.id);
                    box.produce(ball);
                    System.out.println("生产者2查询箱子里面有球 " + box.size() + " 个");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(() -> {
            for (;;){
                try {
                    Ball ball = box.consumer();
                    System.out.println("消费者从箱子拿到球 " + ball.id);
                    System.out.println("消费者查看箱子里面有球 " + box.size() + " 个");
                    //Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(() -> {
            for (;;){
                try {
                    Ball ball = box.consumer();
                    System.out.println("消费者从箱子拿到球 " + ball.id);
                    System.out.println("消费者查看箱子里面有球 " + box.size() + " 个");
                    //Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    static class Ball{
        private String id;
        public Ball(String id){
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
