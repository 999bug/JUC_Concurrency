package com.ncst.lock.rerntrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2020/9/16 19:38
 * @Author by LiShiYan
 * @Descaption 演示ReentrantLock的基本用法，演示被打断
 */
public class LockDemo {
    public static void main(String[] args) {
        LockDemo lockDemo=new LockDemo();
        lockDemo.init();
    }

    private void init(){
        final Outputer outputer=new Outputer();
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputer.output("悟空");
            }
        }).start();

        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputer.output("猪八戒");
            }
        }).start();
    }
    static class Outputer{
        Lock lock=new ReentrantLock();
        public void output(String name){
            int len=name.length();
            lock.lock();
            try{
                for (int i = 0; i < len; i++) {
                    System.out.println(name.charAt(i));
                }
                System.out.println("");
            }finally {
                lock.unlock();
            }
        }
    }
}

