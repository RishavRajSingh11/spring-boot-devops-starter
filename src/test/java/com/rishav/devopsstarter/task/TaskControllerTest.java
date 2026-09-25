package com.rishav.devopsstarter.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TaskControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired MockMvc mockMvc;

    @Test
    void createTaskReturnsCreatedTask() throws Exception {
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"ship the container\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.title").value("ship the container"))
            .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void blankTitleReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("title is required"));
    }

    @Test
    void listTasksReturnsArray() throws Exception {
        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }
}
