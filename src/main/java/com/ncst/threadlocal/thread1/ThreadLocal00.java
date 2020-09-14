package com.ncst.threadlocal.thread1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Date 2020/9/11 17:01
 * @Author by LiShiYan
 * @Descaption 两个线程打印日期
 */
public class ThreadLocal00 {
    public static void main(String[] args) {
        new Thread(() -> {
            String data = new ThreadLocal00().data(10);
            System.out.println(data);
        }
        ) {
        }.start();

        new Thread(() -> {
            String data = new ThreadLocal00().data(323);
            System.out.println(data);
        }
        ) {
        }.start();
    }

    public String data(int seconds) {
        //将秒换算成毫秒
        Date date = new Date(seconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
