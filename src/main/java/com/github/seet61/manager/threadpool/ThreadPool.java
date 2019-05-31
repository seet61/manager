package com.github.seet61.manager.threadpool;

public interface ThreadPool {
    /**
     * Start threads
     */
    int check();

    /**
     * @param runnable
     */
    void execute(Runnable runnable);
}