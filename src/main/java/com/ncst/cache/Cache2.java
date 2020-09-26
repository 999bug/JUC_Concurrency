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
public class Cache2<A,V> implements Computable<A,V> {
    private final Map<A,V> map=new HashMap<>();

    private final Computable<A,V> c;

    public Cache2(Computable<A, V> computable) {
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
        Cache2<String,Integer> cache2=new Cache2<>(new ExpensiveFunction());
        Integer result = cache2.computer("22");
        System.out.println("第一次计算结果："+result);
        result = cache2.computer("22");
        System.out.println("第二次计算结果："+result);
    }
}
