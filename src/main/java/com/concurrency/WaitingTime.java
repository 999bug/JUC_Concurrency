package com.concurrency;

/**
 * @Date 2020/11/29 18:02
 * @Author by LSY
 * @Description 等待超时案例伪代码
 */
public class WaitingTime {
    public synchronized Object get (long mills) throws InterruptedException {
        //超时时间
        long future = System.currentTimeMillis() + mills;
        //剩余时间
        long remaining = mills;
        Object result = null;
        while ((result == null) && remaining > 0){
            wait(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }
}
