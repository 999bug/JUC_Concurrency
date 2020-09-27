package com.ncst.cache;

import com.ncst.cache.computable.Computable;
import com.ncst.cache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Date 2020/9/26 22:16
 * @Author by LiShiYan
 * @Descaption 出于安全性考虑，缓存需要设置有效期，到期自动失效，
 * 否则如果缓存一直不失效，那么带来缓存不一致等问题
 */
public class CachePro<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> map = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);


    private final Computable<A, V> c;

    public CachePro(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 带有过期时间的计算
     *
     * @param arg    要计算的值
     * @param expire 过期时间
     * @return 计算结果
     */
    public V computer(A arg, long expire) throws Exception {
        if (expire > 0) {
            executor.schedule(() -> expire(arg), expire, TimeUnit.MILLISECONDS);
        }
        return computer(arg);
    }

    /**
     *
     * @param arg key
     * @return 获取随机过期时间 的computer
     * @throws Exception d
     */
    public V computerRandom(A arg) throws Exception {
        long i = (long) (Math.random() * 10000);
        return computer(arg, i);
    }

    public synchronized void expire(A key) {
        Future<V> future = map.get(key);
        if (future != null) {
            if (!future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }
            System.out.println("过期时间到，缓存被清除");
            map.remove(key);
        }
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
        CachePro<String, Integer> cache2 = new CachePro<>(new MayFail());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = cache2.computer("666",5000);
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
                    Integer result = cache2.computer("666",5000);
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
                    Integer result = cache2.computer("6667",5000);
                    System.out.println("第一次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(5000);
        Integer result = cache2.computer("666");
        System.out.println("主线程的计算结果：" + result);
    }
}
