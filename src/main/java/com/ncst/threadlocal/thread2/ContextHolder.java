package com.ncst.threadlocal.thread2;

/**
 * @Date 2020/9/13 15:20
 * @Author by LiShiYan
 * @Descaption
 */
public class ContextHolder {

    public static ThreadLocal<Person> personHolder = new ThreadLocal<>();
    public static ThreadLocal<Car> carHolder = new ThreadLocal<>();
}
