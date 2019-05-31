package com.github.seet61.manager.dao;

import com.github.seet61.manager.model.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
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
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TasksDAOTest {
    private static Tasks tasks = new Tasks();
    @Autowired
    private TasksDAO tasksDAO;

    @BeforeClass
    public static void before() {
        tasks.setName("example");
        tasks.setStatus("init");
    }

    @Test
    public void step1_save() {
        log.debug("save");
        tasksDAO.save(tasks);
    }

    @Test
    public void step2_count() {
        assertEquals("count", 1, tasksDAO.count());
    }

    @Test
    public void step3_find() {
        log.debug("findAll: " + tasksDAO.findAll());
        assertEquals("find", tasks, tasksDAO.findByName(tasks.getName()));
    }

    @Test
    public void step4_update() {
        log.debug("update");
        tasksDAO.setStatusByName("done", tasks.getName());
        assertEquals("update", "done", tasksDAO.findByName(tasks.getName()).getStatus());
    }

    @Test
    public void step5_delete() {
        log.debug("delete");
        tasks.setStatus("done");
        tasksDAO.delete(tasks);
    }
}