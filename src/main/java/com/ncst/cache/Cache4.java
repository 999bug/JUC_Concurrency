package com.ncst.cache;

import com.ncst.cache.computable.Computable;
import com.ncst.cache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date 2020/9/26 22:16
 * @Author by LiShiYan
 * @Descaption  缩小了synchronized的粒度，提高性能，但是依然并发不安全
 *  存在重复计算问题
 */
public class Cache4<A,V> implements Computable<A,V> {
    private final Map<A,V> map=new ConcurrentHashMap<>();

    private final Computable<A,V> c;

    public Cache4(Computable<A, V> computable) {
        this.c = computable;
    }

    @Override
    public  V computer(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = map.get(arg);
        if (result==null){
           result = c.computer(arg);
           map.put(arg,result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Cache4<String,Integer> cache2=new Cache4<>(new ExpensiveFunction());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result =cache2.computer("666");
                    System.out.println("第一次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result =cache2.computer("666");
                    System.out.println("第一次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result =cache2.computer("666332");
                    System.out.println("第一次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
