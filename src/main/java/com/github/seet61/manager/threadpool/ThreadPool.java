package com.github.seet61.manager.threadpool;

public interface ThreadPool {
    /**
     * Start threads
     */
    void start();

    /**
     * @param runnable
     */
    void execute(Runnable runnable);
}