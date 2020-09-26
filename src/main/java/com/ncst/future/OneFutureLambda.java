package com.ncst.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Date 2020/9/26 18:14
 * @Author by LiShiYan
 * @Descaption 演示一个future 的使用方法 使用ldamba
 */
public class OneFutureLambda {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(2);
        Callable<Integer> callable=()->{
            return new Random().nextInt(1000);
        };
        Future<Integer> future = service.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

}
