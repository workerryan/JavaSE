package com.dragon.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DelayQueue<MovieTiket> delayQueue = new DelayQueue<MovieTiket>();
        MovieTiket tiket = new MovieTiket("电影票0",10000);
        delayQueue.put(tiket);
        MovieTiket tiket1 = new MovieTiket("电影票1",5000);
        delayQueue.put(tiket1);
        MovieTiket tiket2 = new MovieTiket("电影票2",8000);
        delayQueue.put(tiket2);
        System.out.println("message:--->入队完毕");
        System.out.println(sdf.format(new Date()));

        while( delayQueue.size() > 0 ){
            try {
                tiket = delayQueue.take();
                System.out.println("电影票出队:"+tiket.getMsg() + " time : " + sdf.format(new Date()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MovieTiket implements Delayed {
        //延迟时间
        private final long delay;
        //到期时间
        private final long expire;
        //数据
        private final String msg;
        //创建时间
        private final long now;

        public long getDelay() {
            return delay;
        }

        public long getExpire() {
            return expire;
        }

        public String getMsg() {
            return msg;
        }

        public long getNow() {
            return now;
        }

        /**
         * @param msg 消息
         * @param delay 延期时间
         */
        public MovieTiket(String msg , long delay) {
            this.delay = delay;
            this.msg = msg;
            now = System.currentTimeMillis();
            expire = now + delay;    //到期时间 = 当前时间+延迟时间
        }

        /**
         * @param msg
         */
        public MovieTiket(String msg){
            this(msg,1000);
        }

        public MovieTiket(){
            this(null,1000);
        }

        /**
         * 获得延迟时间   用过期时间-当前时间,时间单位毫秒
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire
                    - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
        }

        /**
         * 用于延迟队列内部比较排序  当前时间的延迟时间 - 比较对象的延迟时间
         * 越早过期的时间在队列中越靠前
         * @param delayed
         * @return
         */
        @Override
        public int compareTo(Delayed delayed) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS)
                    - delayed.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "MovieTiket{" +
                    "delay=" + delay +
                    ", expire=" + expire +
                    ", msg='" + msg + '\'' +
                    ", now=" + now +
                    '}';
        }
    }
}
