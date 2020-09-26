package com.ncst.cache.computable;

/**
 * @Date 2020/9/26 22:17
 * @Author by LiShiYan
 * @Descaption 有一个计算函数computer，用来代表耗时计算，
 *              每个计算器都要实现这个接口，这样就可以无侵入实现缓存功能
 */
public interface Computable<A, V> {

    /**
     *  用来代表耗时计算
     * @param arg 参数
     * @return 计算结r果
     */
    V computer(A arg) throws Exception;
}
