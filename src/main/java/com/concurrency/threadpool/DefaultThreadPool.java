package com.concurrency.threadpool;

import javafx.concurrent.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date 2020/11/29 20:14
 * @Author by LSY
 * @Description
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS =10;
    //线程池默认容量
    private static final int DEFAULT_WORKER_NUMBERS =10;
    //线程池最小容量
    private static final int MIN_WORKER_NUMBERS =10;

    //工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<>();

    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    //工作者线程数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        //初始化线程
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        this.workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS :
                Math.max(num, MIN_WORKER_NUMBERS);
        //初始化线程
        initializeWorkers(workerNum);
    }

    /**
     * 初始化线程工作者
     */
    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker =new Worker();
            workers.add(worker);
            Thread thread =
                    new Thread( worker,"ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            //添加一个工作，然后进行通知
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum = num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            //按照给定的数量停止 Worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 负责消费任务
     */
    class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    //如果工作列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对 WorkerThread 的中断操作，返回
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    }catch (Exception e){

                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
