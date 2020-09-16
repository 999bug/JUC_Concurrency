package com.ncst.lock.rerntrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2020/9/16 19:34
 * @Author by LiShiYan
 * @Descaption 演示多线程预定电影院座位
 */
public class CinemaBookSeat {
    private static ReentrantLock lock=new ReentrantLock();

    public static void main(String[] args) {
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
    }
    private static  void bookSeat(){
        String name = Thread.currentThread().getName();
        lock.lock();
        try {
            System.out.println(name+"开始预定座位");
            Thread.sleep(1000);
            System.out.println(name+"完成预定座位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
