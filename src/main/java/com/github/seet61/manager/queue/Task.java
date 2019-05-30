package com.github.seet61.manager.queue;

import com.github.seet61.manager.controller.ManagerRestController;
import com.github.seet61.manager.dao.TasksDAO;
import com.github.seet61.manager.model.Tasks;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Slf4j
public class Task implements Runnable{
    private String name;
    private JSONObject result = new JSONObject();
    @Autowired
    private ManagerRestController managerRestController;
    @Autowired
    private TasksDAO tasksDAO;


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
                checkPreviousDone(this.name.split("_")[0] + "_1");
                sleep(5);
            } else if (this.name.contains("3")) {
                checkPreviousDone(this.name.split("_")[0] + "_1");
                sleep(10);
            } else {
                checkPreviousDone(this.name.split("_")[0] + "_2");
                checkPreviousDone(this.name.split("_")[0] + "_3");
                sleep(1);
            }
            result.put("name", this.name);
            result.put("status", "done");
            log.debug(Thread.currentThread().getName() + " task " + this.name + " is stopped");
        } catch (InterruptedException | JSONException e) {
            try {
                result.put("name", this.name);
                result.put("status", "error");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            log.error("Thread " + this.name + " was interrupted!");
        }
    }

    private void checkPreviousDone(String name) throws InterruptedException {
        boolean flag = true;
        log.debug(Thread.currentThread() + " checkPreviousDone " + name);
        while(flag) {
            List<Tasks> e = tasksDAO.findByName(name);
            if (!e.get(0).getStatus().equals("done")) {
                Thread.currentThread().sleep(1000);
            } else {
                break;
            }
        }
    }

    private void sleep(int t) throws InterruptedException {
        log.debug(this.name + " sleep " + t + " sec");
        Thread.sleep(t * 1000);
    }
}