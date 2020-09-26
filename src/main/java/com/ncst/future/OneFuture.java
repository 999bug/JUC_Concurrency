package com.ncst.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Date 2020/9/26 18:14
 * @Author by LiShiYan
 * @Descaption 演示一个future 的使用方法
 */
public class OneFuture {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
    public static class CallableTask implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            return new Random().nextInt(10000);
        }
    }
}
