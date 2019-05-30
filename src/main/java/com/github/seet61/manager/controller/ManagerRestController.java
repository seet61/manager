package com.github.seet61.manager.controller;

import com.github.seet61.manager.entities.PingResponse;
import com.github.seet61.manager.service.ManagerRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class ManagerRestController {
    @Autowired
    private ManagerRestService managerRestService;

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public PingResponse ping() {
        return new PingResponse(new Date(), "pong");
    }

    // Add task
    @RequestMapping(value = "/run-new", method = RequestMethod.POST, consumes = "application/json")
    public String runNew(@RequestBody Map<String, Object> reqBody) {
        log.debug("/run-new " + reqBody.toString());
        return managerRestService.runNew(reqBody);
    }

    // Get task
    @RequestMapping(value = "/task-take", method = RequestMethod.POST)
    public String taskTake(@RequestBody Map<String, Object> reqBody) {
        log.debug("/task-take " + reqBody.toString());
        return managerRestService.taskTake(reqBody);
    }

    // Add task
    @RequestMapping(value = "/task-result", method = RequestMethod.POST, consumes = "application/json")
    public String taskResult(@RequestBody Map<String, Object> reqBody) {
        log.debug("/task-result " + reqBody.toString());
        return managerRestService.taskResult(reqBody);
    }

}
