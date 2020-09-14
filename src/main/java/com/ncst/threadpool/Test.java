package com.ncst.threadpool;

/**
 * @Date 2020/9/10 16:52
 * @Author by LiShiYan
 * @Descaption
 */
public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.start();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("线程执行了");
        }
    }
}
