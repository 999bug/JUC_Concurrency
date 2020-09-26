package com.ncst.cache;

import com.ncst.cache.computable.Computable;
import com.ncst.cache.computable.ExpensiveFunction;
import com.ncst.cache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Date 2020/9/26 22:16
 * @Author by LiShiYan
 * @Descaption 利用Future，避免重复计算 使用原子操作 putIfAbsent,
 * 解决缓存污染   map.remove(arg);
 */
public class Cache7<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> map = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Cache7(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V computer(A arg) throws Exception {
        while (true) {
            Future<V> result = map.get(arg);
            if (result == null) {
                Callable<V> callable = () -> c.computer(arg);

                FutureTask<V> ft = new FutureTask<>(callable);
                result = map.putIfAbsent(arg, ft);
                if (result == null) {
                    result = ft;
                    System.out.println("从FutureTask调用了计算函数");
                    ft.run();
                }
            }
            try {
                return result.get();
            } catch (CancellationException e) {
                System.out.println("被取消了");
                map.remove(arg);
                throw e;
            } catch (InterruptedException e) {
                map.remove(arg);
                throw e;
            } catch (ExecutionException e) {
                System.out.println("计算错误，需要重试");
                map.remove(arg);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Cache7<String, Integer> cache2 = new Cache7<>(new MayFail());
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
