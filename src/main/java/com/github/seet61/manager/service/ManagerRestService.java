package com.github.seet61.manager.service;

import com.github.seet61.manager.dao.TasksDAO;
import com.github.seet61.manager.model.Tasks;
import com.github.seet61.manager.queue.Task;
import com.github.seet61.manager.queue.ThreadQueue;
import com.github.seet61.manager.threadpool.ThreadPool;
import com.github.seet61.manager.threadpool.ThreadPoolImpl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ManagerRestService {
    @Autowired
    private ThreadQueue queue = new ThreadQueue();
    @Autowired
    private TasksDAO tasksDAO;

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
        /*Tasks tasksEntity = new Tasks();
        tasksEntity.setName(reqBody.get("name"));
        tasksEntity.setStatus(reqBody.get("status"));*/
        tasksDAO.setStatusByName(reqBody.get("status"), reqBody.get("name"));
        return "";
    }
}
