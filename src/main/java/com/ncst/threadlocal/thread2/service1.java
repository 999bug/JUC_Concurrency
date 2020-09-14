package com.ncst.threadlocal.thread2;

/**
 * @Date 2020/9/13 15:22
 * @Author by LiShiYan
 * @Descaption
 */
public class service1 {
    public static void main(String[] args) {
        new service1().process();
    }

    public void process() {
        Car car = new Car();
        car.setName("宝马");
        ContextHolder.carHolder.set(car);
        new service2().process();
    }
}
