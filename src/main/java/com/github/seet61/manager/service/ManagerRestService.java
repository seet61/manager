package com.github.seet61.manager.service;

import com.github.seet61.manager.dao.TasksDAO;
import com.github.seet61.manager.model.Tasks;
import com.github.seet61.manager.queue.Task;
import com.github.seet61.manager.queue.ThreadQueue;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ManagerRestService {
    @Autowired
    private ThreadQueue queue = new ThreadQueue();
    @Autowired
    private TasksDAO tasksDAO;
    @Autowired
    ApplicationContext ctx;

    public String runNew(Map<String,String> reqBody) {
        for (int i = 1; i < 5; i++) {
            Task task = new Task(reqBody.get("name") + "_" + i);
            synchronized (this.queue) {
                this.queue.put(task);
                this.queue.notifyAll();
            }
            Tasks tasksEntity = new Tasks();
            tasksEntity.setName(reqBody.get("name") + "_" + i);
            tasksEntity.setStatus("init");
            ctx.getAutowireCapableBeanFactory().autowireBean(task);
            tasksDAO.save(tasksEntity);
        }
        return "";
    }

    public JSONObject taskTake() {
        Task task = null;
        try {
            task = (Task) queue.get();
            task.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getResult();
    }

    public String taskResult(Map<String,String> reqBody) {
        tasksDAO.setStatusByName(reqBody.get("status"), reqBody.get("name"));
        return "";
    }
}
