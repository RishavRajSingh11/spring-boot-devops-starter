package com.rishav.devopsstarter.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be at most 100 characters")
    String title
) {}
