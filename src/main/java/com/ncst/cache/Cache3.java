package com.ncst.cache;

import com.ncst.cache.computable.Computable;
import com.ncst.cache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2020/9/26 22:16
 * @Author by LiShiYan
 * @Descaption  解决耦合性：使用装饰者模式解耦 缓存
 *              缺点  性能差 ，锁的粒度大
 */
public class Cache3<A,V> implements Computable<A,V> {
    private final Map<A,V> map=new HashMap<>();

    private final Computable<A,V> c;

    public Cache3(Computable<A, V> computable) {
        this.c = computable;
    }

    @Override
    public synchronized V computer(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = map.get(arg);
        if (result==null){
           result = c.computer(arg);
           map.put(arg,result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Cache3<String,Integer> cache2=new Cache3<>(new ExpensiveFunction());
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
