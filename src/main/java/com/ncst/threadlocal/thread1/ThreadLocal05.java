package com.ncst.threadlocal.thread1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2020/9/11 17:01
 * @Author by LiShiYan
 * @Descaption 利用ThreadLocal，给每个线程分配自己的dateFormat对象，保证了线程安全，高效利用内存
 */
public class ThreadLocal05 {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                String data = new ThreadLocal05().data(finalI);
                System.out.println(data);
            });
        }
        executorService.shutdown();
    }

    public String data(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(seconds * 1000);
        SimpleDateFormat dateFormat = ThreadSafeFormatter.df.get();
        return dateFormat.format(date);
    }

    static class ThreadSafeFormatter {
        public static ThreadLocal<SimpleDateFormat> df =
                ThreadLocal.withInitial(() ->
                        new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss")
                );
    }
}
