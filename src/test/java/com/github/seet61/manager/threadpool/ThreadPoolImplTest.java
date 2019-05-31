package com.github.seet61.manager.threadpool;

import com.github.seet61.manager.queue.Task;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThreadPoolImplTest {
    @Autowired
    private ThreadPoolImpl threadPool;

    @Test
    public void step1_chechStarted() {
        assertEquals("chechStarted", 3, threadPool.check());
    }

    @Test
    public void step2_execute() {
        Task task = new Task("test");
        threadPool.execute(task);
    }
}