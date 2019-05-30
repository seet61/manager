package com.github.seet61.manager.queue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task implements Runnable{
    private String name;

    public Task(String name) {
        this.name = name;
    }

    // Task logic
    @Override
    public void run() {
        try {
            log.debug(Thread.currentThread().getName() + " task " + this.name + " is running");
            if (this.name.contains("1")) {
                sleep(1);
            } else if (this.name.contains("2")) {
                sleep(5);
            } else if (this.name.contains("3")) {
                sleep(10);
            } else {
                sleep(1);
            }
            log.debug(Thread.currentThread().getName() + " task " + this.name + " is stopped");
        } catch (InterruptedException e) {
            log.error("Thread " + this.name + " was interrupted!");
        }

    }

    private void sleep(int t) throws InterruptedException {
        log.debug(this.name + " sleep " + t + " sec");
        Thread.sleep(t * 1000);
    }
}