package com.github.seet61.manager;


import com.github.seet61.manager.threadpool.ThreadPool;
import com.github.seet61.manager.threadpool.ThreadPoolImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("ALL")
@SpringBootApplication
@Slf4j
public class ManagerApp {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPoolImpl(3);
        threadPool.start();
        SpringApplication.run(ManagerApp.class, args);
    }
}
