package com.ncst.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2020/9/17 14:30
 * @Author by LiShiYan
 * @Descaption
 * 演示AtomicInteger 的基本用法，对比非原子类的线程安全问题
 * 使用了原子类，不需要加锁，也可以保证线程安全
 */
public class AtomicIntegerDemo implements Runnable {
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    private static volatile int basicCount = 0;

    public void incrementAtomic() {
        atomicInteger.getAndAdd(2);
//        atomicInteger.getAndIncrement();

    }

    public void incrementBasic(){
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo a=new AtomicIntegerDemo();
        Thread thread1 = new Thread(a);
        Thread thread2 = new Thread(a);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("普通变量的结果：" + basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }
}
