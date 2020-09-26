package com.ncst.flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2020/9/21 17:26
 * @Author by LiShiYan
 * @Descaption 演示condition 基本用法
 */
public class ConditionDemo1 {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo1 c = new ConditionDemo1();
        new Thread(()->{
            try{
                Thread.sleep(1000);
                c.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        c.method1();

    }

    void method1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("条件不满足，开始wait");
            condition.await();
            System.out.println("条件满足了！！");
        } finally {
            lock.unlock();
        }
    }

    void method2() {
        lock.lock();
        try {
            System.out.println("准备弘佐完成，唤醒其他线程");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
