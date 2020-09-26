package com.ncst.collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：组合操作并不保证线程安全
 * 解决办法使用map 自带方法
 */
public class OptionsNotSafe2 implements Runnable {

    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<String, Integer>();
    public static void main(String[] args) throws InterruptedException {
        scores.put("小明", 0);
        Thread t1 = new Thread(new OptionsNotSafe2());
        Thread t2 = new Thread(new OptionsNotSafe2());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(scores);
    }

    //解决办法使用map 自带方法
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            while (true) {
                Integer score = scores.get("小明");
                Integer newScore = score + 1;
                boolean b = scores.replace("小明", score, newScore);
                if (b) {
                    break;
                }
            }
        }
    }
}
