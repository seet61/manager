package com.github.seet61.manager;

import com.github.seet61.manager.controller.ManagerRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerAppTest {
    @Autowired
    private ManagerRestController controller;

    @Test
    public void contextLoads() {
        assertNotNull("contextLoads", controller);
    }
}