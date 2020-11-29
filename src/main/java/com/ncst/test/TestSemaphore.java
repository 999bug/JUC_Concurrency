package com.ncst.test;

import java.util.concurrent.Semaphore;

/**
 * @Date 2020/10/25 21:36
 * @Author by LSY
 * @Description
 */
public class TestSemaphore {
    public static void main(String[] args) {
        int[] a=new int[3];

        StringBuffer stringBuffer=new StringBuffer();
        System.out.println(stringBuffer.hashCode());
        stringBuffer.append(1);
        System.out.println(stringBuffer.hashCode());
        System.out.println("++++++++");
        String s= "2";
        System.out.println(s.hashCode());
        s+="22";
        System.out.println(s.hashCode());
       /* //5台机器  即5个许可证
        Semaphore semaphore = new Semaphore(5);
        for (int worker = 0; worker < 8; worker++) {
            new Worker(worker, semaphore).start();
        }*/
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//抢许可
                System.out.println(num+"");
                Thread.sleep(2000);
                System.out.println("释放");
                semaphore.release();//释放许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
