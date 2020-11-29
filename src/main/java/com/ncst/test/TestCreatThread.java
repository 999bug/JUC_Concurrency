package com.ncst.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Date 2020/11/29 16:54
 * @Author by LSY
 * @Description 创建线程的三种方式
 */
public class TestCreatThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new MyCallable());
        new Thread(task).start();
        System.out.println(task.get());

        Thread myRunnable = new Thread(new MyRunnable());
        myRunnable.start();

        Thread myThread = new Thread(new MyThread());
        myThread.start();
    }

    /**
     * 继承 Thread 方法，重写 run() 方法
     */
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("呦西，好吃的不得了");
        }
    }

    /**
     * 实现Runnable 接口，重写 run() 方法
     */
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("太君土豆哪里去挖？");
        }
    }

    /**
     * 实现Callable() 方法，并且有返回值。
     * 需要使用FutureTask<V> 在外部封装一下获取返回值
     */
    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "我是小鬼子！";
        }
    }
}
