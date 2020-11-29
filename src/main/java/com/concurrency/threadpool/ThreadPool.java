package com.concurrency.threadpool;

/**
 * @Date 2020/11/29 20:01
 * @Author by LSY
 * @Description
 */
public interface ThreadPool<Job extends Runnable> {
    /**
     * 执行一个 job ，这个 job 需要实现 Runnable
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 添加工作者线程
     */
    void addWorkers(int num);

    /**
     * 减少工作者线程
     * @param num
     */
    void removeWorkers(int num);

    /**
     * 得到正在等待执行的任务数量
     * @return
     */
    int getJobSize();
}
