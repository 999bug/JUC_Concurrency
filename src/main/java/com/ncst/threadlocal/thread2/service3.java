package com.ncst.threadlocal.thread2;

/**
 * @Date 2020/9/13 15:22
 * @Author by LiShiYan
 * @Descaption
 */
public class service3 {

    public void process() {
        Car car = ContextHolder.carHolder.get();
        System.out.println("3:"+car.getName());

    }
}
