package com.github.seet61.manager.threadpool;

import com.github.seet61.manager.queue.ThreadQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolImpl implements ThreadPool {
    private final int nThreads;
    private final PoolWorker[] threads;
    private ThreadQueue queue;

    public ThreadPoolImpl(int nThreads) {
        this.nThreads = nThreads;
        queue = new ThreadQueue();
        threads = new PoolWorker[nThreads];
    }

    /**
     * Запускает потоки.
     * Потоки бездействуют, до тех пор пока не появится новое задание в очереди
     */
    @Override
    public void start() {
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
        log.debug("Thread pool started!");
    }

    /**
     * складывает это задание в очередь.
     * Освободившийся поток должен выполнить это задание.
     * Каждое задание должны быть выполнено ровно 1 раз
     * @param runnable задача
     */
    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.put(runnable);
            queue.notifyAll();
        }
    }

    /**
     * Обработчик пула
     */
    private class PoolWorker extends Thread {
        public void run() {

            while (true) {
                synchronized (queue) {
                    while (queue.getSize() == 0) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Runnable task = (Runnable) queue.get();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
