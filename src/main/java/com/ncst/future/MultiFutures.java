package com.ncst.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Date 2020/9/26 18:22
 * @Author by LiShiYan
 * @Descaption 演示批量提交任务时，用List来批量接收结果
 */
public class MultiFutures {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);

        Callable<Integer> callable = () -> {
            return new Random().nextInt(10000);
        };

        List<Future<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = service.submit(callable);
            lists.add(future);
        }
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = lists.get(i);
            Integer integer = future.get();
            System.out.println("integer = " + integer);
        }


    }
}
