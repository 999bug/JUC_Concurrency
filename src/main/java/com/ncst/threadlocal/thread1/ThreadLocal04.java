package com.ncst.threadlocal.thread1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2020/9/11 17:01
 * @Author by LiShiYan
 * @Descaption 1000 个打印日期 使用静态对象 ,用线程池来执行 使用加锁的方式
 * 问题： 出现重复的时间
 *      解决重复的时间
 */
public class ThreadLocal04 {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                String data = new ThreadLocal04().data(finalI);
                System.out.println(data);
            });
        }
        executorService.shutdown();
    }

    public String data(int seconds) {
        //将秒换算成毫秒
        String s;
        Date date = new Date(seconds * 1000);
        //加锁
        synchronized (ThreadLocal04.class){
            s=sdf.format(date);
        }
        return s;
    }
}
