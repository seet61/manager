package com.github.seet61.manager.threadpool;

import com.github.seet61.manager.queue.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolImplTest {
    @Autowired
    private ThreadPoolImpl threadPool;

    @Test
    public void chechStarted() {
        assertEquals("chechStarted", 3, threadPool.start());
    }

    @Test
    public void execute() {
        Task task = new Task("test");
        threadPool.execute(task);
    }
}