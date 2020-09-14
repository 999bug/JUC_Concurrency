package com.ncst.threadlocal.thread1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Date 2020/9/11 17:01
 * @Author by LiShiYan
 * @Descaption 1000 个打印日期
 */
public class ThreadLocal01 {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                String data = new ThreadLocal01().data(finalI);
                System.out.println(data);
            }
            ) {
            }.start();
        }
    }

    public String data(int seconds) {
        //将秒换算成毫秒
        Date date = new Date(seconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
