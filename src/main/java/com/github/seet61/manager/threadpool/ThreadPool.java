package com.github.seet61.manager.threadpool;

public interface ThreadPool {
    /**
     * Start threads
     */
    int start();

    /**
     * @param runnable
     */
    void execute(Runnable runnable);
}