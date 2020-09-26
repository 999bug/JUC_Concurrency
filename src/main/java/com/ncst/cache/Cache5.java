package com.ncst.cache;

import com.ncst.cache.computable.Computable;
import com.ncst.cache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Date 2020/9/26 22:16
 * @Author by LiShiYan
 * @Descaption 利用Future，避免重复计算 ，存在重复的可能,当两个线程挨的特别近的时候，存在重复计算
 */
public class Cache5<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> map = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Cache5(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V computer(A arg) throws Exception {
        Future<V> result = map.get(arg);
        if (result == null) {
            Callable<V> callable = () -> c.computer(arg);

            FutureTask<V> ft = new FutureTask<>(callable);
            result = ft;
            map.put(arg, ft);

            System.out.println("从FutureTask调用了计算函数");
            ft.run();

        }
        return result.get();
    }

    public static void main(String[] args) throws Exception {
        Cache5<String, Integer> cache2 = new Cache5<>(new ExpensiveFunction());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = cache2.computer("666");
                    System.out.println("第一次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = cache2.computer("666");
                    System.out.println("第一次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = cache2.computer("666332");
                    System.out.println("第一次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
