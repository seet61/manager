package com.github.seet61.manager.controller;

import com.github.seet61.manager.ManagerApp;
import com.github.seet61.manager.entities.PingResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
public class ManagerRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ManagerRestController controller;

    @Test
    public void step1_ping() {
        PingResponse example = new PingResponse(new Date(), "pong");
        PingResponse response = controller.ping();
        assertEquals("ping", example.getStatus(), response.getStatus());
    }

    @Test
    public void step2_runNew() throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "example");
        mvc.perform(post("/run-new")
                .content(reqBody.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void step3_taskTake() throws Exception {
        mvc.perform(post("/task-take")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void step4_taskResult() throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "example_1");
        reqBody.put("status", "done");
        mvc.perform(post("/task-result")
                .content(reqBody.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}