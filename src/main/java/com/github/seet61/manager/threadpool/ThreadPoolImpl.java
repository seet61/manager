package com.github.seet61.manager.threadpool;

import com.github.seet61.manager.controller.ManagerRestController;
import com.github.seet61.manager.queue.ThreadQueue;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DependsOn("threadQueue")
public class ThreadPoolImpl implements ThreadPool {
    private int nThreads;
    private PoolWorker[] threads;
    @Autowired
    private ThreadQueue queue = new ThreadQueue();
    @Autowired
    private ManagerRestController managerRestController;

    ThreadPoolImpl() {
        this.nThreads = 3;
        this.threads = new PoolWorker[nThreads];
        for (int i = 0; i < nThreads; i++) {
            this.threads[i] = new PoolWorker();
            this.threads[i].start();
        }
        log.debug("Thread pool started!");
    }

    public int check() {
        return threads.length;
    }

    /**
     * складывает это задание в очередь.
     * Освободившийся поток должен выполнить это задание.
     * Каждое задание должны быть выполнено ровно 1 раз
     * @param runnable задача
     */
    @Override
    public void execute(Runnable runnable) {
        synchronized (this.queue) {
            this.queue.put(runnable);
            this.queue.notifyAll();
        }
    }

    /**
     * Обработчик пула
     */
    private class PoolWorker extends Thread {
        public void run() {
            log.debug(Thread.currentThread().getName() + " was started");
            while (true) {

                log.debug(Thread.currentThread().getName() + " check messages");
                while (queue.getSize() == 0) {
                    try {
                        log.debug(Thread.currentThread().getName() + " waiting for messages. Queue size: " + queue.getSize());
                        Thread.currentThread().sleep(1000);
                        //queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    log.debug(Thread.currentThread().getName() + " start execute task");
                    managerRestController.taskTake();
                } catch (JSONException e) {
                    log.error(e.getStackTrace().toString());
                }
            }
        }
    }
}
