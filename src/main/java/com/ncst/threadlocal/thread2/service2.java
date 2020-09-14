package com.ncst.threadlocal.thread2;

/**
 * @Date 2020/9/13 15:22
 * @Author by LiShiYan
 * @Descaption
 */
public class service2 {

    public void process() {

        Car car = ContextHolder.carHolder.get();
        System.out.println("2:"+car.getName());
        ContextHolder.carHolder.remove();
        Car car1=new Car();
        car1.setName("奔驰");
        ContextHolder.carHolder.set(car1);
        new service3().process();

    }
}
