package com.rishav.devopsstarter.task;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final AtomicLong sequence = new AtomicLong();
    private final ConcurrentHashMap<Long, TaskResponse> tasks = new ConcurrentHashMap<>();

    public TaskResponse create(CreateTaskRequest request) {
        long id = sequence.incrementAndGet();
        TaskResponse task = new TaskResponse(id, request.title(), false);
        tasks.put(id, task);
        return task;
    }

    public List<TaskResponse> findAll() {
        return tasks.values().stream()
            .sorted(java.util.Comparator.comparingLong(TaskResponse::id))
            .toList();
    }
}
