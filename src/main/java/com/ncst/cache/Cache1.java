package com.ncst.cache;

import java.util.HashMap;

/**
 * @Date 2020/9/26 21:57
 * @Author by LiShiYan
 * @Descaption 使用hashMap 实现简单缓存
 * 缺点：性能差， 耦合性强 锁的粒度大
 */
public class Cache1 {
    private final HashMap<String, Integer> cache = new HashMap<>();

    public synchronized Integer compute(String id) throws InterruptedException {
        Integer result = cache.get(id);
        if (result == null) {
            result = doCompute(id);
            cache.put(id, result);
        }
        return result;
    }

    private Integer doCompute(String id) throws InterruptedException {
        Thread.sleep(5000);
        return Integer.parseInt(id);
    }

    public static void main(String[] args) throws InterruptedException {
        Cache1 cache1 = new Cache1();

        System.out.println("开始计算了");
        Integer result = cache1.compute("25");

        System.out.println("第一次计算结果：" + result);
        result = cache1.compute("25");
        System.out.println("第二次计算结果：" + result);
    }
}
