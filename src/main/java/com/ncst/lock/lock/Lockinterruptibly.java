package com.ncst.lock.lock;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2020/9/14 20:29
 * @Author by LiShiYan
 * @Descaption 在等待锁的时候，锁会被中断
 */
public class Lockinterruptibly  implements Runnable {
    private Lock lock=new ReentrantLock();

    public static void main(String[] args) {
    Lockinterruptibly lint=new Lockinterruptibly();
        Thread thread1 = new Thread(lint);
        Thread thread2 = new Thread(lint);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(name+":获取到了锁");
                Thread.sleep(5000);
            }catch (InterruptedException e){
                System.out.println(name+ ":睡眠期间被中断了");

            }finally {
                lock.unlock();
                System.out.println(name + ":释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(name+":获得索期间被中断了");
        }
    }
}
