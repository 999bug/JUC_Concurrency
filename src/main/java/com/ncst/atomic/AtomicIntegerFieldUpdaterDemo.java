package com.ncst.atomic;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Date 2020/9/17 15:13
 * @Author by LiShiYan
 * @Descaption 演示AtomicIntegerFieldUpdater 用法
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {

    static Candiate tom;
    static Candiate mike;

    public AtomicIntegerFieldUpdaterDemo() {
        tom = new Candiate();
        mike = new Candiate();
    }

    public static AtomicIntegerFieldUpdater<Candiate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candiate.class, "score");

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerFieldUpdaterDemo a = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(a);
        Thread t2 = new Thread(a);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("普通变量：" + tom.score);
        System.out.println("升级后的结果" + mike.score);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            tom.score++;
            scoreUpdater.getAndIncrement(mike);
        }
    }

    static class Candiate {
        volatile int score;
    }
}
