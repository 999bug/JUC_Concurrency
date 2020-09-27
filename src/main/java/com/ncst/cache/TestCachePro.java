package com.ncst.cache;

import com.ncst.cache.computable.ExpensiveFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2020/9/27 9:51
 * @Author by LiShiYan
 * @Descaption 测试缓存的高并发
 */
public class TestCachePro {
    static CachePro<String, Integer> cache = new CachePro<>(new ExpensiveFunction());
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(100);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            service.submit(()->{
                Integer result=null;
                try {
                    System.out.println(Thread.currentThread().getName() + "开始等待");
                    countDownLatch.await();
                    SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormattter.get();
                    String format = dateFormat.format(new Date());
                    System.out.println(Thread.currentThread().getName()+"   "+format+"被放行");
                   result= cache.computer("3443");
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(6000);
        countDownLatch.countDown();
        service.shutdown();
    }

    static class ThreadSafeFormatter {
        public static ThreadLocal<SimpleDateFormat> dateFormattter=new ThreadLocal<SimpleDateFormat>(){
            //每个线程会调用本方法一次，用于初始化
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("mm:ss");
            }

            //首次调用本方法时，会调用initialValue()；后面的调用会返回第一次创建的值
            @Override
            public SimpleDateFormat get() {
                return super.get();
            }
        };
    }
}
