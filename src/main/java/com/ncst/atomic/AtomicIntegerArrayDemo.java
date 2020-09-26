package com.ncst.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Date 2020/9/17 14:45
 * @Author by LiShiYan
 * @Descaption 演示原子数组的使用方法
 */
public class AtomicIntegerArrayDemo {
    private static final int num=100;

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray=new AtomicIntegerArray(num);
        Increment increment=new Increment(atomicIntegerArray);
        Decrement decrement=new Decrement(atomicIntegerArray);

        Thread[] threadsInc = new Thread[num];
        Thread[] threadsDesc = new Thread[num];
        for (int i = 0; i < num; i++) {
            threadsInc[i]=new Thread(increment);
            threadsDesc[i]=new Thread(decrement);

            threadsInc[i].start();
            threadsDesc[i].start();
        }

        //需要对每一个线程进行join
        for (int i = 0; i < num; i++) {
            try{
                threadsInc[i].join();
                threadsDesc[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //打印结果
        int len=atomicIntegerArray.length();
        for (int i = 0; i < len; i++) {
            if (atomicIntegerArray.get(i)!=0) {
                System.out.println("发现了错误"+i);
            }
        }
        System.out.println("运行结束");
    }

    static class Increment implements Runnable {
        private AtomicIntegerArray array;

        public Increment(AtomicIntegerArray array) {
            this.array = array;
        }

        @Override
        public void run() {
            int len = array.length();
            for (int i = 0; i < len; i++) {
                array.getAndIncrement(i);
            }
        }
    }

    static class Decrement implements Runnable {
        private AtomicIntegerArray array;

        public Decrement(AtomicIntegerArray array) {
            this.array = array;
        }

        @Override
        public void run() {
            int len = array.length();
            for (int i = 0; i < len; i++) {
                array.getAndDecrement(i);
            }
        }
    }
}
