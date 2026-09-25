package com.rishav.devopsstarter.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) { this.repository = repository; }

    public TaskResponse create(CreateTaskRequest request) {
        TaskEntity saved = repository.save(new TaskEntity(request.title()));
        log.info("Task created: taskId={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        return repository.findAllByOrderByIdAsc().stream().map(this::toResponse).toList();
    }

    private TaskResponse toResponse(TaskEntity entity) {
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.isCompleted());
    }
}
