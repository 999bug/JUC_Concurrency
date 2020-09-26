package com.ncst.future;

import java.util.concurrent.*;

/**
 * @Date 2020/9/26 18:27
 * @Author by LiShiYan
 * @Descaption 演示get方法过程中抛出异常，for循环为了演示抛出Exception的时机：
 * 并不是说一产生异常就抛出，直到我们get执行时，才会抛出。
 */
public class GetException {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        Callable<Integer> callable = () -> {
            throw new IllegalArgumentException("Callable抛出异常");
        };
        Future<Integer> future = service.submit(callable);

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                Thread.sleep(500);
            }
            System.out.println(future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException异常");
        }
    }
}
