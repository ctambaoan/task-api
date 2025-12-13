package ctambaoan.taskapi.dto;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import java.time.LocalDateTime;

public record TaskResponse(Long id,
                           String name,
                           String description,
                           TaskStatus status,
                           LocalDateTime created) {

    public static TaskResponse map(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreated());
    }
}